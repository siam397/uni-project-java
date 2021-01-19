const mongoose=require("mongoose")
const profileSchema=new mongoose.Schema({
    user_id:String,
    username:String,
    bio:String,
    profilePicture:String,
    posts:[String]
})
const Profile=new mongoose.model("Profile",profileSchema)
module.exports=Profile