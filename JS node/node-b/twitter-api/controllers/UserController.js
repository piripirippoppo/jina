var db = require('../index');
const { body } = require('express-validator/check');
const { validationResult } = require('express-validator/check');

// Retrieve list of all Users
exports.user_list = function(req, res) {
    db.User.find({})
    .then(function(dbUsers) {
      res.json(dbUsers);
    })
    .catch(function(err) {
      res.json(err);
    })
};

// Retrieve list of all tweets
// Tweets do not store a reference to whom they belong

exports.tweet_list = function(req,res) {
  db.Tweet.find({})
    .then(function(dbTweets) {
      res.json(dbTweets);
    })
    .catch(function(err) {
      res.json(err);
    })
}

// Create a user
exports.create_user = function(req, res, next) {
  try {
    const errors = validationResult(req); // Finds the validation errors in this request and wraps them in an object with handy functions

    if (!errors.isEmpty()) {
      console.log(errors)
      res.status(422).json({ errors: errors.array() });
      return;
    }   
    db.User.create(req.body)
      .then(function(dbUser) {
      // If we were able to successfully create a Product, send it back to the client
        res.json(dbUser);
      })
      .catch(function(err) {
        // If an error occurred, send it to the client
        res.json(err);
    });
  } catch(err) {
    return next(err)
  }
}

// Create a tweet and reference it in the appropriate user
exports.create_tweet = function(req, res, next) {
  try {
    const errors = validationResult(req); // Finds the validation errors in this request and wraps them in an object with handy functions

    if (!errors.isEmpty()) {
      console.log(errors)
      res.status(422).json({ errors: errors.array() });
      return;
    } 
    // Create a new tweet and pass the req.body to the entry
    db.Tweet.create(req.body)
      .then(function(dbTweet) {
      // If a Tweet was created successfully, find one user with an `_id` equal to `req.params.id`. Update the User to be associated with the new Tweet
      // { new: true } tells the query that we want it to return the updated User -- it returns the original by default
      // Since our mongoose query returns a promise, we can chain another `.then` which receives the result of the query
      return db.User.findOneAndUpdate({ _id: req.params.id }, {$push: {tweets: dbTweet._id}}, { new: true });
    })
    .then(function(dbUser) {
      // If we were able to successfully update a User, send it back to the client
      res.json(dbUser);
    })
    .catch(function(err) {
      // If an error occurred, send it to the client
      res.json(err);
    });
  } catch(err) {
    return next(err)
  }
}

// Retrieve a single user and all of the associated tweets
exports.get_user = function(req, res) {
  // Using the id passed in the id parameter, prepare a query that finds the matching one in our db...
  db.User.findOne({ _id: req.params.id })
  // ..and populate all of the tweets associated with it
  .populate("tweets")
  .then(function(dbUser) {
    // If we were able to successfully find a User with the given id, send it back to the client
    res.json(dbUser);
  })
  .catch(function(err) {
    // If an error occurred, send it to the client
    res.json(err);
  });
}

//custom validations
// only done on create methods
exports.validate = (method) => {
  switch (method) {
    case 'create_user': {
      console.log('validating new user')
     return [ 
        body('name', 'name doesn\'t exist').exists(),
        body('login', 'login doesn\'t exist').exists(),
        body('password', 'password isn\'t long enough').isLength({min: 6}),
        body('email', 'email doesn\'t exist').exists(),
        body('email', 'email is not a valid email').isEmail()
       ]   
    }
    case 'create_tweet': {
      console.log('validating new tweet')
     return [ 
        body('content', 'content of tweet is too long').isLength({max: 150}),
       ]   
    }
  }
}



