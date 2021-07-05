const mongoose=require("mongoose")
const profileSchema=new mongoose.Schema({
    user_id:String,
    username:String,
    bio:String,
    profilePicture:String,
    friends:[{
        user_id:String,
        username:String,
        bio:String,
        profilePicture:String
    }],
    friendRequests:[{
        user_id:String,
        username:String,
        bio:String,
        profilePicture:String
    }],
    sentFriendRequests:[{
        user_id:String,
        username:String,
        bio:String,
        profilePicture:String
    }]
})
const Profile=new mongoose.model("Profile",profileSchema)
module.exports=Profile