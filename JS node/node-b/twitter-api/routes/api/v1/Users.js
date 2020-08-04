var express = require('express');
var userRouter = express.Router();
var Usercontroller = require('../../../controllers/UserController');

/*`/api/v1/users` - this will return all the documents inside the `users` collection. */   
userRouter.route('/users').get(Usercontroller.user_list);
/*`/api/v1/user`  - this will generate a new user document inside of the `users` collection.*/ 
userRouter.route('/user').post(Usercontroller.validate('create_user'),Usercontroller.create_user);
/*`/api/v1/tweets` - this will return all the documents inside the `tweets` collection.*/
userRouter.route('/tweets').get(Usercontroller.tweet_list);
/*`/api/v1/user/:id` - this will generate a new document inside the `tweets` collection 
 * as well as generate a reference to the document inside the `users` collection that has 
 * a value of `:id` for its `_id` field. */
  userRouter.route('/user/:id')
  .post(Usercontroller.validate('create_tweet'), Usercontroller.create_tweet)
/* `/api/v1/users/:id` - this will return the document that matches the `:id` request param 
 * as well as all the tweets that it references. 
 * __Note__: the operation that you will be performing in this endpoint inside of mongo is 
 * identical to a join method in a tradition SQL database. */
  userRouter.route('/users/:id').get(Usercontroller.get_user);
  
  module.exports = userRouter;