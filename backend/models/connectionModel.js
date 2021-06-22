const mongoose=require("mongoose")
const connectionSchema=new mongoose.Schema({
    connect:{},
    ids:[String],
    messages:[]

})
const Connection=new mongoose.model("Connection",connectionSchema)
module.exports=Connection