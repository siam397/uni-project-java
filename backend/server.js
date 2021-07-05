const express = require('express')
const SocketServer=require("websocket").server
var bodyParser = require('body-parser')
const http=require("http")
const mongoose=require("mongoose")
const cors=require("cors")
const server=http.createServer((req,res)=>{})
const app = express()
app.use(cors());

app.use(bodyParser.json())
const routes=require("./routes/postRoutes");
const Connection=require("./models/connectionModel")

mongoose.connect("mongodb+srv://pervyshrimp:123@cluster0.ydugl.mongodb.net/UniProjectDB",{ useNewUrlParser: true, useUnifiedTopology: true  })
mongoose.set('useNewUrlParser', true);
mongoose.set('useFindAndModify', false);
mongoose.set('useCreateIndex', true);
const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  console.log("connected")
});

app.use(routes);


server.listen(4000,()=>{
  console.log("listening for chat")
})

app.listen("3000",function(){
    console.log("server started")
})


wsServer=new SocketServer({
  httpServer:server,
  maxReceivedFrameSize: 1000000,
  maxReceivedMessageSize: 10 * 1024 * 1024,
  autoAcceptConnections: false
})
const connections=[]
const allmessages=[]

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
  console.log(messages)
  messages.message.forEach(mess=>{
    connection.connect.sendUTF(mess)
  })
  connections.push(connection)
  connection.connect.on("message",(mes)=>{
    messages.message.push(mes.utf8Data)
    console.log(mes.utf8Data)
    connections.forEach(element=>{
      if(element.ids[0]==connection.ids[1] && element.ids[1]==connection.ids[0]){
        element.connect.sendUTF(mes.utf8Data)
      }
    })
    
    
    
    for(let i=0;i<allmessages.length;i++){
      console.log("url comparison" +"this is splitted::" +splittedurl +"::this  is saved :::"+allmessages[i].url)
      var condition1=allmessages[i].url[0]===splittedurl[0] &&allmessages[i].url[1]===splittedurl[1] 
      var condition2=allmessages[i].url[1]===splittedurl[0] &&allmessages[i].url[0]===splittedurl[1]
      var sendto=condition1 || condition2 
      if(sendto){
        console.log("before"+allmessages[i].message)
        allmessages[i]=messages;
        console.log("after"+allmessages[i].message)
      }
    }
    connection.connect.on("close",(resCode,des)=>{
      console.log(des+"     "+resCode)
      connections.splice(connections.indexOf(connection),1)
    })
  })
})





























// wsServer.on("request",(req)=>{
  
//   var url=req.resourceURL.pathname
//   url=url.replace("/","")
//   const splittedurl=url.split(",")
//   console.log(req);
//   var connection={
//     connect:req.accept(),
//     ids:splittedurl,
//     messages:[]
//   }
//   // console.log(messages)
//   connection.messages.forEach(message=>{
//     connection.connect.sendUTF(message)
//   })
//   // connections.push(connection)
//   connection.connect.on("message",(mes)=>{
//     connection.messages.push(mes.utf8Data)
//     connections.forEach(element=>{
//       if(element.ids[0]==connection.ids[1] && element.ids[1]==connection.ids[0]){
//         console.log("true");
//           element.connect.sendUTF(mes.utf8Data)
//           element.messages.push(mes.utf8Data)
//           connections.push(connection)
//           for(let i=0;i<connections.length;i++){
//             if(connections[i].ids[0]==connection.ids[0]  &&  connections[i].ids[1]==connection.ids[1]){
//               connections[i]=connection;
//               break;
//             }
//           }
    
          
//       }
//     })
  

//     connection.connect.on("close",(resCode,des)=>{
//       console.log(des+"     "+resCode)
//       connections.splice(connections.indexOf(connection),1)
//     })
//   })
  
// })






















// console.log(req);
  // const connection={
  //   connect:req.accept(),
  //   ids:splittedurl,
  //   messages:[]
  // }
// var found=false;
//   for(let i=0;i<connections.length;i++){
//     if(connections[i].ids[0]==connections[i].ids[0]  &&  connections[i].ids[1]==connections[i].ids[1]){
//       found=true;
//       var connection=connections[i];
//     }
//   }
  
//   var connection=connections.map(item=>{
//     if(item.ids[0]==splittedurl[0] && item.ids[1]==splittedurl[1]){
//       return item
//     }
//   })

//   if(!found){
//     const connection={
//     connect:req.accept(),
//     ids:splittedurl,
//     messages:[]
//   }
//     connections.push(connection)
//   }

//   console.log("found"+found);

//   // console.log(messages)
//   connection.messages.forEach(message=>{
//     connection.connect.sendUTF(message)
//   })
//   // connections.push(connection)
//   connection.connect.on("message",(mes)=>{
//     connection.messages.push(mes.utf8Data)
//     connections.forEach(element=>{
//       if(element.ids[0]==connection.ids[1] && element.ids[1]==connection.ids[0]){
//         console.log("true");
//           element.connect.sendUTF(mes.utf8Data)
//           element.messages.push(mes.utf8Data)
//           connections.push(connection)
//           for(let i=0;i<connections.length;i++){
//             if(connections[i].ids[0]==connection.ids[0]  &&  connections[i].ids[1]==connection.ids[1]){
//               connections[i]=connection;
//               break;
//             }
//           }
    
          
//       }
//     })
  

//     connection.connect.on("close",(resCode,des)=>{
//       console.log(des+"     "+resCode)
//       connections.splice(connections.indexOf(connection),1)
//     })
//   })