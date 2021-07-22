package com.example.artsell;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.example.artsell.utilities.Variables.url_2;

public class ChatRoomActivity extends AppCompatActivity implements TextWatcher{
    private String name;
    private String friendname;
    private WebSocket webSocket;
    private String SERVER_PATH="";

    public void setSERVER_PATH(String SERVER_PATH) {
        this.SERVER_PATH = SERVER_PATH;
    }
    private EditText messageEdit;
    private TextView textView;
    private String profilePicture;
    private CircleImageView circleImageView;
    private RecyclerView recyclerView;
    private Button sendbtn;
    private String friendId;
    private ImageView pickimage;
    private final int GALLERY_REQUEST=1;
    private final int camera_request=2;
    private MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendId=getIntent().getStringExtra("id");
        setContentView(R.layout.activity_chat_room);
        textView=findViewById(R.id.friend_name);
        circleImageView=findViewById(R.id.profile_image);
        name=getIntent().getStringExtra("username");
        System.out.println("this is name"+name);
        textView.setText(name);
        String baselink=url_2;
        SharedPreferences sharedPreferences=getBaseContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String userInfo=sharedPreferences.getString("user","");
        setSERVER_PATH(baselink+friendId+","+userInfo);
        //needs to change
        friendname=getIntent().getStringExtra("username");
        profilePicture=getIntent().getStringExtra("profilePicture");
        System.out.println(profilePicture);
        byte[] image=Base64.decode(profilePicture,Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(image,0,image.length);
        circleImageView.setImageBitmap(bitmap);
        name=sharedPreferences.getString("username","");
        initiateSocketConnection();
    }

    private void initiateSocketConnection() {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(SERVER_PATH)
                .build();
        webSocket=client.newWebSocket(request,new SocketListener());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String trimmedMessage=s.toString().trim();
        if(trimmedMessage.isEmpty()){
            resetMessageEdit();
        }else{
            pickimage.setVisibility(View.INVISIBLE);
            sendbtn.setVisibility(View.VISIBLE);
        }
    }

    private void resetMessageEdit() {
        messageEdit.removeTextChangedListener(this);
        messageEdit.setText("");
        pickimage.setVisibility(View.VISIBLE);
        sendbtn.setVisibility(View.INVISIBLE);
        messageEdit.addTextChangedListener(this);
    }


    //socketListener

    private class SocketListener extends WebSocketListener{
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            runOnUiThread(ChatRoomActivity.this::initializeView);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            runOnUiThread(()->{
                try{
                    JSONObject jsonObject=new JSONObject(text);
                    Log.w("TAG", "onMessage: "+jsonObject.getString("name"));
                    if(jsonObject.getString("name").equals(name)){
                        jsonObject.put("isSent",true);
                    }else{
                        jsonObject.put("isSent",false);
                    }
                    messageAdapter.addItem(jsonObject);
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
    }

    private void initializeView() {

        messageEdit=findViewById(R.id.editmessage);
        sendbtn=findViewById(R.id.sendButton);
        pickimage=findViewById(R.id.sendImage);
        recyclerView=findViewById(R.id.recyclerview);
        messageAdapter=new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageEdit.addTextChangedListener(this);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject=new JSONObject();
                try{
                    jsonObject.put("name",name);
                    jsonObject.put("message",messageEdit.getText().toString());
                    webSocket.send(jsonObject.toString());
                    jsonObject.put("isSent",true);
                    messageAdapter.addItem(jsonObject);
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
                    resetMessageEdit();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        pickimage.setOnClickListener(v -> {
            try{
                if (ActivityCompat.checkSelfPermission(ChatRoomActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChatRoomActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST);
                }
                System.out.println("hello");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GALLERY_REQUEST);
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);

            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
                try{
                    InputStream is = getContentResolver().openInputStream(data.getData());
                    Bitmap image = BitmapFactory.decodeStream(is);

                    sendImage(image);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    private void sendImage(Bitmap image) {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,50,baos);
        String base64img=Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);
        JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put("name", name);
            jsonObject.put("image", base64img);
            System.out.println("hocche");
            System.out.println("this is the one: "+base64img);
            webSocket.send(jsonObject.toString());
            jsonObject.put("isSent",true);
            messageAdapter.addItem(jsonObject);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}