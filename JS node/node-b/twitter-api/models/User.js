var mongoose = require("mongoose");

var Schema = mongoose.Schema;

var UserSchema = new Schema(
  {/*name - it should be a String and a required field.*/
    name: {type: String, required: true}, 
/* login - it should be a String and a required field. 
 * It represents the login a user will use to authenticate into the application system.**/
    login: {type: String, required: true},
/* password - it should be a String and a required field. When you add user authentication to 
 * the application stack you will use the login and password combination to authenticate a user.**/
    password: {type: String, required: true},
/* email - it should be a String and required. */
    email: {type: String, required: true},
    /* tweets - it should be defined like this:
```javascript
tweets: [{  
  type: Schema.Types.ObjectId,  
  ref: 'Tweet'  
}]  
``` **/
    tweets: [{
        type: Schema.Types.ObjectId,
        ref: 'Tweet'
    }]
  }
);

module.exports = mongoose.model('User', UserSchema);