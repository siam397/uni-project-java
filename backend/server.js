const express = require('express')
const SocketServer=require("websocket").server
var bodyParser = require('body-parser')
const http=require("http")
const lodash=require("lodash")
const mongoose=require("mongoose")
const cors=require("cors")
const server=http.createServer((req,res)=>{})
const app = express()
const fs=require('fs')
app.use(cors());
const firebaseRef=require("./firebase/firebaseInitialize")
app.use(bodyParser.json())
const postRoutes=require("./routes/postRoutes");
const getRoutes=require("./routes/getRoutes");
const Connection=require("./models/connectionModel")
mongoose.connect("mongodb+srv://pervyshrimp:123@cluster0.ydugl.mongodb.net/UniProjectDB",{ useNewUrlParser: true, useUnifiedTopology: true  })
mongoose.set('useNewUrlParser', true);
mongoose.set('useFindAndModify', false);
mongoose.set('useCreateIndex', true);
const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));

allmessages=[]
db.once('open', async function() {
  console.log("connected")
  
  const find=await getMessages();
  allmessages=find[0].messages
});
const Profile=require("./models/profileModels")

app.use(postRoutes);
app.use(getRoutes);


server.listen(4000,()=>{
  console.log("listening for chat")
})

app.listen("3000",function(){
    console.log("server started")
})




//web socket starts here

const messagesDB=require("./models/messageModel")
const message=new messagesDB({
  _id:"0",
  messages:[]
})
// message.save()
const getMessages=async()=>{
  return await messagesDB.find({})
}
const connections=[]

wsServer=new SocketServer({
  httpServer:server,
  maxReceivedFrameSize: 1000000,
  maxReceivedMessageSize: 10 * 1024 * 1024,
  autoAcceptConnections: false
})



const allRecentChats=[]
wsServer.on("request",(req)=>{
  var url=req.resourceURL.pathname
  url=url.replace("/","")
  const splittedurl=url.split(",")
  const connection={
    connect:req.accept(),
    ids:splittedurl
  }
  
  var found=false;
  var messages={
    url:splittedurl,
    message:[]
  }
  for(let i=0;i<allmessages.length;i++){
    var condition1=allmessages[i].url[0]===splittedurl[0] &&allmessages[i].url[1]===splittedurl[1] 
    var condition2=allmessages[i].url[1]===splittedurl[0] &&allmessages[i].url[0]===splittedurl[1]
    var sendto=condition1 || condition2 
    if(sendto){
      messages=allmessages[i];
      found=true;
      break;
    }
  }
  if(!found) allmessages.push(messages)
  messages.message.forEach(mess=>{
    connection.connect.sendUTF(mess)
  })
  connections.push(connection)


  connection.connect.on("message",async (mes)=>{
    messages.message.push(mes.utf8Data)
    const usersRef = firebaseRef.child('users');
    const snapshot = await firebaseRef.once('value');
    const value = snapshot.val();
    var objmes=JSON.parse(mes.utf8Data)
    const url1=splittedurl[0];
    const url2=splittedurl[1]
    
    const jsonString = fs.readFileSync('./profilePictures.json')
    var customer = JSON.parse(jsonString)
    var url1Image=customer[url1]
    var url2Image=customer[url2]


    //saves chat to firebase
    if(value[splittedurl[0]] === "created"){
      var value1={}
      var profile=await Profile.findOne({user_id:url2});
      var chatNameurl2=profile.username;
      if(objmes.message==null){
        value1[url1]=[{id:url2,profilePicture:url2Image,message:"faludaBoizzz9252image",name:objmes.name,toPerson:chatNameurl2,fromPersonId:objmes.id}];
      }else{
        value1[url1]=[{id:url2,profilePicture:url2Image,message:objmes.message,name:objmes.name,toPerson:chatNameurl2,fromPersonId:objmes.id}];
      }
      
      firebaseRef.update(value1)
      
      
    }else if(value[splittedurl[0]] !== "created"){
      const snapshot = await firebaseRef.once('value');
      var profile=await Profile.findOne({user_id:url2});
      var chatNameurl2=profile.username;
      
      const value = snapshot.val();
      value[url1] = lodash.reject(value[url1],person=>{
        return person.id==url2
      })
      if(objmes.message==null){
        value[url1]=[{id:url2,profilePicture:url2Image,message:"faludaBoizzz9252image",name:objmes.name,toPerson:chatNameurl2,fromPersonId:objmes.id},...value[url1]];
      }else{
        value[url1]=[{id:url2,profilePicture:url2Image,message:objmes.message,name:objmes.name,toPerson:chatNameurl2,fromPersonId:objmes.id},...value[url1]];
      }
      firebaseRef.update(value)
    }


    if(value[splittedurl[1]] === "created" ){
      var value1={}
      var profile=await Profile.findOne({user_id:url1});
      var chatNameurl1=profile.username;
      
      if(objmes.message==null){
        value1[url2]=[{id:url1,profilePicture:url1Image,message:"faludaBoizzz9252image",name:objmes.name,toPerson:chatNameurl1,fromPersonId:objmes.id}];
      }else{
        value1[url2]=[{id:url1,profilePicture:url1Image,message:objmes.message,name:objmes.name,toPerson:chatNameurl1,fromPersonId:objmes.id}];
      }
      firebaseRef.update(value1)
      
    }else if(value[splittedurl[1]] !== "created"){
      const snapshot = await firebaseRef.once('value');
      const value = snapshot.val();
      var profile=await Profile.findOne({user_id:url1});
      var chatNameurl1=profile.username;
      value[url2] = lodash.reject(value[url2],person=>{
        return person.id==url1
      })
      if(objmes.message==null){
        value[url2]=[{id:url1,profilePicture:url1Image,message:"faludaBoizzz9252image",name:objmes.name,toPerson:chatNameurl1,fromPersonId:objmes.id},...value[url2]];
      }else{
        value[url2]=[{id:url1,profilePicture:url1Image,message:objmes.message,name:objmes.name,toPerson:chatNameurl1,fromPersonId:objmes.id},...value[url2]];
      }
      firebaseRef.update(value)
    }




    connections.forEach(element=>{
      if(element.ids[0]==connection.ids[1] && element.ids[1]==connection.ids[0]){
        element.connect.sendUTF(mes.utf8Data)
      }
    })
    
    
    
    for(let i=0;i<allmessages.length;i++){
      var condition1=allmessages[i].url[0]===splittedurl[0] &&allmessages[i].url[1]===splittedurl[1] 
      var condition2=allmessages[i].url[1]===splittedurl[0] &&allmessages[i].url[0]===splittedurl[1]
      var sendto=condition1 || condition2 
      if(sendto){
        allmessages[i]=messages;
      }
    }
    connection.connect.on("close",async (resCode,des)=>{
      connections.splice(connections.indexOf(connection),1)

      // ...
      const doc = await messagesDB.findOne({_id:"0"});
      doc.messages=allmessages;
      await doc.save()
      
    })
  })
})



