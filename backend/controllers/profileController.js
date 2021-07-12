const Profile=require("../models/profileModels")
const Friends=require("../models/friendsModel")
const lodash=require('lodash');
const { result } = require("lodash");
visited={};
exports.getProfileInfo=(req,res)=>{
    const id=req.body.ID;
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
    const firstPersonFriends = await Friends.findOne({user_id:firstId})
    const userInfo={
        user_Id:randomUser.user_id,
        username:randomUser.username,
        bio:randomUser.bio,
        profilePicture:randomUser.profilePicture, 
    }
    if(lodash.find(firstPersonFriends.friends, ['user_id',secondId])){
        userInfo["friend"]=true;
    }else if(!lodash.find(firstPersonFriends.friends, ['user_id',secondId])){
        userInfo["friend"]=false;
    }
    if( !userInfo["sentRequest"] && lodash.find(firstPersonFriends.sentFriendRequests, ['user_id',secondId]) ){
        userInfo["sentRequest"]=true;
    }else if(!lodash.find(firstPersonFriends.sentFriendRequests, ['user_id',secondId])){
        userInfo["sentRequest"]=false;
    }
    if( !userInfo["getRequest"] && lodash.find(firstPersonFriends.friendRequests, ['user_id',secondId]) ){
        userInfo["getRequest"]=true;
    }else if(!lodash.find(firstPersonFriends.friendRequests, ['user_id',secondId])){
        userInfo["getRequest"]=false;
    }
    
    res.send(userInfo)
}

exports.getSuggestedUsers= async (req,res)=>{
    const id=req.body.id;
    const userFriends = await Friends.findOne({user_id : id});
    var list=[];
    let listOfSuggestedPeople=[];
    try{
        for (const friend of userFriends.friends){
        
            list=[...list,...await newBfs(id)]
        }
        // user.friends.forEach(async friend => {
        //     i++
        //     list= await bfs(friend.user_id,id);
        //     if(i===user.friends.length)console.log(list)
        // });
        for(const userId of list){
            listOfSuggestedPeople.push(await Profile.findOne({user_id:userId}))
        }
        res.status(201).send(listOfSuggestedPeople)
    }catch(e){
        res.status(501).send(e)
    }
}


// bfs=async (friendId,id)=>{
//     var queue=[friendId]
//     const mainUser = await Profile.findOne({user_id : id});
//     result=[]
//     var currentFriend;
//     visited[friendId]=true;
//     while(queue.length!=0){
//         currentFriend=queue.shift()
//         if(currentFriend.user_id===id){
//             continue;
            
//         }else{
//             currentFriendUser=await Profile.findOne({user_id : currentFriend});
//             if(currentFriend!==friendId  && !friends(mainUser,currentFriendUser) && !sentRequest(mainUser,currentFriendUser) && !requested(mainUser,currentFriendUser) ){
//                 result.push(currentFriendUser);
//             }
//             currentFriendUser.friends.forEach(neighborFriend=>{
//                 if(!visited[neighborFriend.user_id] && neighborFriend.user_id!==id){
//                     visited[neighborFriend.user_id]=true;
//                     queue.push(neighborFriend.user_id)
//                 }
//             })
//         }
//     }
//     return result
// }

newBfs=async(id)=>{
    var queue=[id]
    const mainUser = await Friends.findOne({user_id : id});
    var result=[]
    var currentFriend;
    visited[id]=true;
    while(queue.length!==0){
        currentFriend=queue.shift()
        currentFriendUser=await Friends.findOne({user_id : currentFriend});
        if(currentFriend!==id  && !friends(mainUser,currentFriendUser) && !sentRequest(mainUser,currentFriendUser) && !requested(mainUser,currentFriendUser) ){
            result.push(currentFriend);
        }
        currentFriendUser.friends.forEach(neighborFriend=>{
            if(!visited[neighborFriend.user_id] && neighborFriend.user_id!==id){
                visited[neighborFriend.user_id]=true;
                queue.push(neighborFriend.user_id)
            }
        })
    }
    return result;
}

friends=(mainUser,friend)=>{
    return lodash.find(mainUser.friends,function(o){
        return (o.user_id===friend.user_id)
    })
}
sentRequest=(mainUser,friend)=>{
    return lodash.find(mainUser.sentFriendRequests,function(o){
        return (o.user_id===friend.user_id)
    })
}
requested=(mainUser,friend)=>{
    return lodash.find(mainUser.friendRequests,function(o){
        return (o.user_id===friend.user_id)
    })
}