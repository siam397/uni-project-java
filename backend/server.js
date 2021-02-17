const express = require('express')
const WebSocket=require('ws')
const SocketServer=require("websocket").server
const http=require("http")
const mongoose=require("mongoose")
const cors=require("cors")
const server=http.createServer((req,res)=>{})
const app = express()
app.use(cors());

app.use(express.json())

const routes=require("./routes/postRoutes");

mongoose.connect("mongodb+srv://pervyshrimp:123@peoplecluster.ydugl.mongodb.net/UniProjectDB",{ useNewUrlParser: true, useUnifiedTopology: true  })
mongoose.set('useNewUrlParser', true);
mongoose.set('useFindAndModify', false);
mongoose.set('useCreateIndex', true);
const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  console.log("connected")
});

app.use(routes);


// server.listen(4000,()=>{
//   console.log("listening for chat")
// })

app.listen("3000",function(){
    console.log("server started")
})

// wsServer=new SocketServer({
//   httpServer:server,
//   maxReceivedFrameSize: 1000000,
//   maxReceivedMessageSize: 10 * 1024 * 1024,
//   autoAcceptConnections: false
// })
// const connections=[]
// const messages=[]

// wsServer.on("request",(req)=>{
//   const connection=req.accept()
//   console.log(messages)
//   messages.forEach(message=>{
//     connection.sendUTF(message)
//   })
//   connections.push(connection)
//   connection.on("message",(mes)=>{
//     messages.push(mes.utf8Data)
//     console.log(mes.utf8Data)
//     connections.forEach(element=>{
//       console.log(element);
//       if(element!=connection){
//         element.sendUTF(mes.utf8Data)
//       }

//     })

//     connection.on("close",(resCode,des)=>{
//       console.log(des+"     "+resCode)
//       connections.splice(connections.indexOf(connection),1)
//     })
//   })
// })

const messages=[]
const wss = new WebSocket.Server({ port: 4000 });

wss.on('connection', function connection(ws) {
  messages.forEach(message=>{
    ws.send(message)
  })
  ws.on('message', function incoming(message) {
    console.log('received: %s', message);
    messages.push(message)
  });

  ws.send('something');
});



