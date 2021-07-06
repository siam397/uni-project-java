const Profile=require("../models/profileModels")
const lodash=require('lodash')

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

exports.getAnotherUser=async (req,res)=>{
    const firstId=req.body.firstId;
    const secondId=req.body.secondId;
    const randomUser = await Profile.findOne({user_id:secondId})
    const firstPerson = await Profile.findOne({user_id:firstId})
    const userInfo={
        user_Id:randomUser.user_id,
        username:randomUser.username,
        bio:randomUser.bio,
        profilePicture:randomUser.profilePicture, 
    }
    if(lodash.find(firstPerson.friends, ['user_id',secondId])){
        userInfo["friend"]=true;
        userInfo["sentRequest"]=false;
        userInfo["getRequest"]=false;
    }else if(!lodash.find(firstPerson.friends, ['user_id',secondId])){
        userInfo["friend"]=false;
    }
    if( !userInfo["sentRequest"] && lodash.find(firstPerson.sentFriendRequests, ['user_id',secondId]) ){
        userInfo["friend"]=false;
        userInfo["sentRequest"]=true;
        userInfo["getRequest"]=false;
    }else if(!lodash.find(firstPerson.sentFriendRequests, ['user_id',secondId])){
        userInfo["sentRequest"]=false;
    }
    if( !userInfo["getRequest"] && lodash.find(firstPerson.friendRequests, ['user_id',secondId]) ){
        userInfo["friend"]=false;
        userInfo["sentRequest"]=false;
        userInfo["getRequest"]=true;
    }else if(!lodash.find(firstPerson.friendRequests, ['user_id',secondId])){
        userInfo["getRequest"]=false;
    }
    
    res.send(userInfo)
}

