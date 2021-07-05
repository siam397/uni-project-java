const express=require("express");
const userController=require("../controllers/userController")
const profileController=require("../controllers/profileController")
const router=express.Router();

router.post("/login",userController.login);
router.post("/signup",userController.signup);
router.post("/getProfileInfo",profileController.getProfileInfo)
router.post("/getFriends",profileController.getFriends)
router.post('/addFriend',profileController.acceptRequest)
router.post('/sendRequest',profileController.sendRequest)
module.exports=router;