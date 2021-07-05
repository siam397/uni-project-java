const express=require("express");
const userController=require("../controllers/userController")
const profileController=require("../controllers/profileController")
const friendsController=require("../controllers/friendController")
const router=express.Router();

router.post("/login",userController.login);
router.post("/signup",userController.signup);
router.post("/getProfileInfo",profileController.getProfileInfo)
router.post("/getFriends",friendsController.getFriends)
router.post('/addFriend',friendsController.acceptRequest)
router.post('/sendRequest',friendsController.sendRequest)
router.post('/removeFriend',friendsController.removeFriend)
router.post('/removeRequest',friendsController.removeRequest)
module.exports=router;