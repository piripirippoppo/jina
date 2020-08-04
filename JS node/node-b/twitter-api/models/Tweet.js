var mongoose = require("mongoose");

var Schema = mongoose.Schema;

// Defines what individual author document looks like
var TweetSchema = new Schema(
  {
    content: {type: String, required: true}, //content - it should be defined as a String and required.   
    likes: {type: Number, required: true}, // likes - it should be defined as a Number and required.  
    dislikes: {type: Number, required: true} // dislikes - it should be defined as a Number and required. 
  }
);

module.exports = mongoose.model('Tweet', TweetSchema);