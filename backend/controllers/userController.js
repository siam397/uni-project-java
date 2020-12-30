const User=require("../models/userModels")
const bcrypt=require("bcrypt");
const { use } = require("../routes/postRoutes");

exports.login=async (req,res)=>{
    const email=req.body.email;
    const password=req.body.password;
    User.findOne({email:email},async (err,user)=>{
        console.log(user)
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
            console.log(err)
            return;
        }
        if(user.length!==0){
            res.status(420).send("email exists");
        }else{
            
            
                const hashedPassword=await bcrypt.hash(password.toString(),10)
                console.log(hashedPassword)
                const newUser=new User({
                    email:email,
                    username:username,
                    password:hashedPassword
                })
                newUser.save()
                res.send({
                    username:newUser.username,
                    email:newUser.email,
                    _id:newUser._id
                })
        }
    })
}