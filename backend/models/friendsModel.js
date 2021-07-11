const mongoose=require("mongoose")
const friendSchema=new mongoose.Schema({
    user_id:String,
    friends:[{
        user_id:String,
        username:String,
        profilePicture:String
    }],
    friendRequests:[{
        user_id:String,
        username:String,
        profilePicture:String
    }],
    sentFriendRequests:[{
        user_id:String,
        username:String,
        profilePicture:String
    }]
})
const Friends=new mongoose.model("Friend",friendSchema)
module.exports=Friends