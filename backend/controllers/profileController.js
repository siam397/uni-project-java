const Profile=require("../models/profileModels")

exports.getProfileInfo=(req,res)=>{
    id=req.body.id;
    Profile.find({user_id:id},function(err,profileInfo){
        console.log(profileInfo)
    })
}
// exports.changeProfilePicture=(req,res)=>{
//     const image=req.body.dp
//     const userID=req.body.id;
//     User.find({})
// }