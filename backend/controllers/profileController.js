const Profile=require("../models/profileModels")
exports.getProfileInfo=(req,res)=>{
    const id=req.body.ID;
    console.log("this is id"+id);
    Profile.findOne({user_id:id},function(err,profileInfo){
        
        if(err){
            console.log(err)
        }else{
            console.log(profileInfo.username)
            res.send({
                username:profileInfo.username,
                bio:profileInfo.bio,
                profilePicture:profileInfo.profilePicture,
                friends:profileInfo.friends
            })
            
        }
    })
}

