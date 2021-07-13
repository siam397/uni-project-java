require('dotenv').config()
const firebase = require('firebase');
const config=require('../config');
const firebaseConfig = {
    apiKey: process.env.API_KEY,
    authDomain: process.env.AUTH_DOMAIN,
    databaseURL: process.env.DATABASE_URL,
    projectId: process.env.PROJECT_ID,
    storageBucket: process.env.STORAGE_BUCKET,
    messagingSenderId: process.env.MESSAGING_SENDER_ID,
    appId: process.env.APP_ID,
    measurementId: process.env.MEASUREMENT_ID
  };
  console.log(firebaseConfig)
const fbdb = firebase.initializeApp(firebaseConfig);
const db = fbdb.database();
const ref = db.ref('users');
module.exports = ref;