const Profile=require("../models/profileModels")
const _=require("lodash")
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
    try{
        const firstPerson=await Profile.findOne({user_id:firstId});
        const secondPerson=await Profile.findOne({user_id:secondId});
        firstPerson.friends.push(secondPerson)
        secondPerson.friends.push(firstPerson)
        firstPerson.friendRequests= _.reject(firstPerson.friendRequests,person=>{
            return person.user_id==secondId;
        })
        secondPerson.sentFriendRequests = _.reject(secondPerson.sentFriendRequests,person=>{
            return person.user_id==firstId;
        })
        firstPerson.save();
        secondPerson.save();
        console.log(firstPerson.friends);
        console.log(secondPerson.friends);
        res.status(201)
    }catch(e){
        console.log(e)
        res.status(501)
    }
}

exports.sendRequest=async (req,res)=>{
    const firstId=req.body.firstId;
    const secondId=req.body.secondId;
    try{
        const firstPerson=await Profile.findOne({user_id:firstId});
        const secondPerson=await Profile.findOne({user_id:secondId});
        firstPerson.sentFriendRequests.push(secondPerson);
        secondPerson.friendRequests.push(firstPerson);
        firstPerson.save();
        secondPerson.save();
        res.status(201)
    }catch(e){
        console.log(e)
        res.status(501)
    }
}

exports.removeRequest = async (req,res)=>{
    removeFriendRequest(req.body.firstId,req.body.secondId);
}

exports.removeFriend=async (req,res)=>{
    const firstId=req.body.firstId;
    const secondId=req.body.secondId;
    try{
        unfriend(firstId,secondId);
        res.status(201)
    }catch(e){
        console.log(e)
        res.status(501)
    }
}


removeFriendRequest=async (firstId,secondId)=>{
    const firstPerson=await Profile.findOne({user_id:firstId});
    const secondPerson=await Profile.findOne({user_id:secondId});
    firstPerson.friendRequests= _.reject(firstPerson.friendRequests,person=>{
        return person.user_id==secondId;
    })
    secondPerson.sentFriendRequests = _.reject(secondPerson.sentFriendRequests,person=>{
        return person.user_id==firstId;
    })
    firstPerson.save();
    secondPerson.save();
}


unfriend = async (firstId,secondId)=>{
    const firstPerson=await Profile.findOne({user_id:firstId});
    const secondPerson=await Profile.findOne({user_id:secondId});
    firstPerson.friends = _.reject(firstPerson.friends,person=>{
        return person.user_id == secondId;
    }) 
    secondPerson.friends = _.reject(secondPerson.friends,person=>{
        return person.user_id==firstId;
    })
    firstPerson.save();
    secondPerson.save();
}
