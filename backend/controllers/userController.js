const User=require("../models/userModels")
const Profile=require("../models/profileModels")
const Friend=require("../models/friendsModel")
const defaultDP=require("../utilities/defaultProfilePicture")
const bcrypt=require("bcrypt");
const fs=require('fs')
const firebaseRef = require('../firebase/firebaseInitialize');
exports.login=async (req,res)=>{
    const email=req.body.email;
    const password=req.body.password;
    User.findOne({email:email},async (err,user)=>{
        if(user.length==0){
            res.status(504).send("email doesnt exist")
        }else{
            try{
                if(bcrypt.compare(req.body.password, user.password)){
                    res.send({
                        username:user.username,
                        email:user.email,
                        _id:user._id
                    })
                }else{
                    res.status(504).send("password mismatch")
                }
            }catch{
                res.status(500).send("something went wrong")
            }
        }
    })
    
}

exports.signup=async (req,res)=>{
    const email=req.body.email;
    const username=req.body.username;
    const password=req.body.password;
    User.find({email:email},async function(err,user){
        if(err){
            res.status(501).send(err)
        }
        if(user.length!==0){
            res.status(420).send("email exists");
        }else{
            try{
                const hashedPassword=await bcrypt.hash(password.toString(),10)
                const newUser=new User({
                    email:email,
                    username:username,
                    password:hashedPassword
                })
                newUser.save()
                const user_id=newUser._id;
                const jsonString = fs.readFileSync('./profilePictures.json')
                var customer = JSON.parse(jsonString)
                customer[newUser._id]=defaultDP
                fs.writeFileSync('./profilePictures.json', JSON.stringify(customer))
                const usersRef = firebaseRef.child(`${user_id}`);
                usersRef.set('created')
                const newProfile=new Profile({
                    user_id:newUser._id,
                    username:newUser.username,
                    bio:"",
                    profilePicture:"",
                    friends:[]
                })
                newProfile.save()
                const newFriends=new Friend({
                    user_id:newUser._id,
                    friends:[],
                    friendRequests:[],
                    sentFriendRequests:[]
                })
                newFriends.save();
                
                res.status(201).send({
                    username:newUser.username,
                    email:newUser.email,
                    _id:newUser._id
                })
            }catch(e){
                res.status(501).send(e)
            }    
        }
    })
}