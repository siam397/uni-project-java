const express=require("express");
const userController=require("../controllers/userController")
const profileController=require("../controllers/profileController")
const router=express.Router();

router.post("/login",userController.login);
router.post("/signup",userController.signup);
router.post("/getProfileInfo",profileController.getProfileInfo)
module.exports=router;