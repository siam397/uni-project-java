const mongoose=require("mongoose")
const messageSchema=new mongoose.Schema({
    _id:String,
    messages:[Object]
})
const Message=new mongoose.model("Message",messageSchema)
module.exports=Message