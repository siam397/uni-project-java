const Profile=require("../models/profileModels")
const Friends=require("../models/friendsModel")
const _=require("lodash")
const fs=require('fs')
exports.getFriends=async (req,res)=>{
    const id=req.body.ID;
    const friends=[]
    const jsonString = fs.readFileSync('./profilePictures.json')
    var customer = JSON.parse(jsonString)
    var fren={};
    var allFriends=await Friends.findOne({user_id:id})
    allFriends=allFriends.friends;
    for(const friend of allFriends){
        fren={
            _id:friend._id,
            user_id:friend.user_id,
            username:friend.username,
            bio:friend.bio,
            profilePicture:customer[friend.user_id]
        }
        friends.push(fren)
    }
    res.status(201).send({
        friends:friends
    })
    // Friends.findOne({user_id:id},function(err,profileInfo){
    //     console.log(profileInfo.friends)
    //     if(err){
    //         console.log(err)
    //     }else{
    //         res.send({
    //             friends:profileInfo.friends
    //         })
    //     }
    // })
}

exports.acceptRequest=async (req,res)=>{
    const firstId=req.body.firstId;
    const secondId=req.body.secondId;
    try{
        const firstPerson=await Profile.findOne({user_id:firstId});
        const secondPerson=await Profile.findOne({user_id:secondId});
        const firstPersonFriends=await Friends.findOne({user_id:firstId});
        const secondPersonFriends=await Friends.findOne({user_id:secondId});
        firstPersonFriends.friends.push(secondPerson)
        secondPersonFriends.friends.push(firstPerson)
        firstPersonFriends.friendRequests= _.reject(firstPersonFriends.friendRequests,person=>{
            return person.user_id==secondId;
        })
        secondPersonFriends.sentFriendRequests = _.reject(secondPersonFriends.sentFriendRequests,person=>{
            return person.user_id==firstId;
        })
        // firstPerson.save();
        // secondPerson.save();
        firstPersonFriends.save();
        secondPersonFriends.save();
        res.status(201).send({
            response:"accepted"
        })
    }catch(e){
        res.status(501).send(e)
    }
}

exports.sendRequest=async (req,res)=>{
    const firstId=req.body.firstId;
    const secondId=req.body.secondId;
    try{
        const firstPerson=await Profile.findOne({user_id:firstId});
        const secondPerson=await Profile.findOne({user_id:secondId});
        const firstPersonFriends=await Friends.findOne({user_id:firstId});
        const secondPersonFriends=await Friends.findOne({user_id:secondId});
        if(!sentRequest(firstPersonFriends,secondPersonFriends) && !requested(secondPersonFriends,firstPersonFriends)){
            firstPersonFriends.sentFriendRequests.push(secondPerson);
            secondPersonFriends.friendRequests.push(firstPerson);
            firstPersonFriends.save();
            secondPersonFriends.save();
            console.log("hoise")
            res.status(201).send({
                response:"sent"
            })
        }
        
    }catch(e){
        res.status(501).send(e)
    }
}

exports.removeRequest = async (req,res)=>{
    try{
        removeFriendRequest(req.body.firstId,req.body.secondId);
        res.status(201).send({
            response:"removedRequest"
        })
    }catch(e){
        res.status(501).send(e)
    }
}

exports.removeFriend=async (req,res)=>{
    const firstId=req.body.firstId;
    const secondId=req.body.secondId;
    try{
        unfriend(firstId,secondId);
        res.status(201).send({
            response:"removedFriend"
        })
    }catch(e){
        res.status(501).send(e)
    }
}

exports.getRequests=async(req,res)=>{
    const id=req.body.ID;
    try{
        const requests=await Friends.findOne({user_id:id});
        res.status(201).send(requests.friendRequests)
    }catch(e){
        res.status(420).send(e)
    }
}


removeFriendRequest=async (firstId,secondId)=>{
    const firstPersonFriends=await Friends.findOne({user_id:firstId});
    const secondPersonFriends=await Friends.findOne({user_id:secondId});

    firstPersonFriends.friendRequests = _.reject(firstPersonFriends.friendRequests,person=>{
        return person.user_id==secondId;
    })
    firstPersonFriends.sentFriendRequests = _.reject(firstPersonFriends.sentFriendRequests,person=>{
        return person.user_id==secondId;
    })
    secondPersonFriends.sentFriendRequests = _.reject(secondPersonFriends.sentFriendRequests,person=>{
        return person.user_id==firstId;
    })
    secondPersonFriends.friendRequests = _.reject(secondPersonFriends.friendRequests,person=>{
        return person.user_id==firstId;
    })
    firstPersonFriends.save();
    secondPersonFriends.save();
}


unfriend = async (firstId,secondId)=>{
    const firstPersonFriends=await Friends.findOne({user_id:firstId});
    const secondPersonFriends=await Friends.findOne({user_id:secondId});
    firstPersonFriends.friends = _.reject(firstPersonFriends.friends,person=>{
        return person.user_id == secondId;
    }) 
    secondPersonFriends.friends = _.reject(secondPersonFriends.friends,person=>{
        return person.user_id==firstId;
    })
    firstPersonFriends.save();
    secondPersonFriends.save();
}



friends=(mainUser,friend)=>{
    return _.find(mainUser.friends,function(o){
        return (o.user_id===friend.user_id)
    })
}
sentRequest=(mainUser,friend)=>{
    return _.find(mainUser.sentFriendRequests,function(o){
        return (o.user_id===friend.user_id)
    })
}
requested=(mainUser,friend)=>{
    return _.find(mainUser.friendRequests,function(o){
        return (o.user_id===friend.user_id)
    })
}