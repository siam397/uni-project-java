const Profile=require("../models/profileModels")
const Friends=require("../models/friendsModel")
const lodash=require('lodash');
const fs=require('fs')
visited={};
exports.getProfileInfo=(req,res)=>{
    const id=req.body.ID;
    const jsonString = fs.readFileSync('./profilePictures.json')
    var customer = JSON.parse(jsonString)
    Profile.findOne({user_id:id},function(err,profileInfo){
        
        if(err){
            console.log(err)
        }else{
            res.status(201).send({
                username:profileInfo.username,
                bio:profileInfo.bio,
                profilePicture:customer[id],
                friends:profileInfo.friends
            })
            
        }
    })
}

exports.getAnotherUser=async (req,res)=>{
    const firstId=req.body.firstId;
    const secondId=req.body.secondId;
    console.log(firstId,secondId)
    const randomUser = await Profile.findOne({user_id:secondId})
    const firstPerson = await Profile.findOne({user_id:firstId})
    const firstPersonFriends = await Friends.findOne({user_id:firstId})
    const jsonString = fs.readFileSync('./profilePictures.json')
    var customer = JSON.parse(jsonString)
    const userInfo={
        user_Id:randomUser.user_id,
        username:randomUser.username,
        bio:randomUser.bio,
        profilePicture:customer[secondId], 
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
    
    res.status(201).send(userInfo)
}




exports.getSuggestedUsers= async (req,res)=>{
    const id=req.body.ID;
    const userFriends = await Friends.findOne({user_id : id});
    var peopleFriends;
    const user = await Profile.findOne({user_id : id});
    let userInfo;
    var list=[];
    let listOfSuggestedPeople=[];
    try{
        list=[...list,...await bfs(id)]
        if(list.length===0){
            for(const people of await Profile.find()){
                peopleFriends = await Friends.findOne({user_id : people.user_id});
                if(people.user_id!==id && !friends(userFriends,peopleFriends) && !sentRequest(userFriends,peopleFriends) && !requested(userFriends,peopleFriends)){
                    userInfo={
                        ...people,
                        friends:false,
                        sentFriendRequests:false,
                        requested:false,
                    }
                    list.push(userInfo)
                }
            }
        }else{
            var people;
            for(const userId of list){
                people=await Profile.findOne({user_id:userId})
                listOfSuggestedPeople.push({
                    _id:people._id,
                    user_id:people.user_id,
                    username:people.username,
                    bio:people.bio,
                    profilePicture:"",
                    friends:false,
                    sentFriendRequests:false,
                    requested:false,
                })
                
            }
            list=[...listOfSuggestedPeople]
        }
        console.log(list)
        res.status(201).send({
            suggestedPeople:list
        })
    }catch(e){
        res.status(501).send(e)
    }
}




exports.getAllUser= async (req,res)=>{
    const allUser=await Profile.find();
    try{
        res.status(200).send(allUser)
    }catch(e){
        res.status(500).send(e)
    }
}





bfs=async(id)=>{
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