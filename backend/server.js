const express = require('express')
const SocketServer=require("websocket").server
var bodyParser = require('body-parser')
const http=require("http")
const lodash=require("lodash")
const mongoose=require("mongoose")
const cors=require("cors")
const server=http.createServer((req,res)=>{})
const app = express()
app.use(cors());
const firebaseRef=require("./firebase/firebaseInitialize")
app.use(bodyParser.json())
const routes=require("./routes/postRoutes");
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

app.use(routes);


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


    //saves chat to firebase
    if(value[splittedurl[0]] === "created"){
      const url1=splittedurl[0];
      const url2=splittedurl[1]
      var value1={}
      value1[url1]=[{id:url2,message:mes.utf8Data}];
      firebaseRef.update(value1)
      
      
    }else if(value[splittedurl[0]] !== "created"){
      const url1=splittedurl[0];
      const url2=splittedurl[1]
      const snapshot = await firebaseRef.once('value');
      const value = snapshot.val();
      value[url1] = lodash.reject(value[url1],person=>{
        return person.id==url2
      })
      value[url1]=[{id:url2,message:mes.utf8Data},...value[url1]];
      firebaseRef.update(value)
    }


    if(value[splittedurl[1]] === "created"){
      const url1=splittedurl[0];
      const url2=splittedurl[1]
      var value1={}
      value1[url2]=[{id:url1,message:mes.utf8Data}];
      firebaseRef.update(value1)
      
    }else if(value[splittedurl[1]] !== "created"){
      const url1=splittedurl[0];
      const url2=splittedurl[1]
      const snapshot = await firebaseRef.once('value');
      const value = snapshot.val();
      value[url2] = lodash.reject(value[url2],person=>{
        return person.id==url1
      })
      value[url2]=[{id:url1,message:mes.utf8Data},...value[url2]];
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
      await messagesDB.deleteOne({ _id:"0" })
      const message=new messagesDB({
        _id:"0",
        messages:allmessages
      })
      message.save()
    })
  })
})
