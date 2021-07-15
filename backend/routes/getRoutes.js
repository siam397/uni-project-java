const express=require("express");

const profileController=require("../controllers/profileController")
const router=express.Router();



router.get('/getAllUser',profileController.getAllUser)






module.exports=router;