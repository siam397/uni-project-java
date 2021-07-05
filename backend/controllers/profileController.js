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

exports.getFriends=(req,res)=>{
    console.log("ashse");
    const id=req.body.ID;
    console.log("this is id"+id);
    Profile.findOne({user_id:id},function(err,profileInfo){
        
        if(err){
            console.log(err)
        }else{
            console.log(profileInfo.friends)
            res.send({
                friends:profileInfo.friends
            })
        }
    })
}

exports.acceptRequest=async (req,res)=>{
    const firstId=req.body.firstId;
    const secondId=req.body.secondId;
    const firstPerson=await Profile.findOne({user_id:firstId});
    const secondPerson=await Profile.findOne({user_id:secondId});
    firstPerson.friends.push(secondPerson)
    secondPerson.friends.push(firstPerson)
    firstPerson.save();
    secondPerson.save();
    console.log(firstPerson.friends);
    console.log(secondPerson.friends);
}

exports.sendRequest=async (req,res)=>{
    const firstId=req.body.firstId;
    const secondId=req.body.secondId;
    const firstPerson=await Profile.findOne({user_id:firstId});
    const secondPerson=await Profile.findOne({user_id:secondId});
    firstPerson.sentFriendRequests.push(secondPerson);
    secondPerson.friendRequests.push(firstPerson);
    firstPerson.save();
    secondPerson.save();
}
