const express = require('express')
const mongoose=require("mongoose")
const cors=require("cors")
const app = express()
app.use(cors());

app.use(express.json())

const routes=require("./routes/postRoutes");




mongoose.connect("mongodb+srv://pervyshrimp:123@peoplecluster.ydugl.mongodb.net/User",{ useNewUrlParser: true, useUnifiedTopology: true  })
mongoose.set('useNewUrlParser', true);
mongoose.set('useFindAndModify', false);
mongoose.set('useCreateIndex', true);
const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  console.log("connected")
});
app.use(routes);


app.listen("3000",function(){
    console.log("server started")
})