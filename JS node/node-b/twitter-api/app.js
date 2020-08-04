var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var mongoose = require('mongoose');
var cors = require('cors')
var bodyParser = require('body-parser');
var usersRouter = require('./routes/api/v1/Users');

var app = express();
app.use(express.json());
//app.use(cors)
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use('/api/v1/', usersRouter);

// TODO 
// set user and password to the user you created for the teaching staff in exercise 2
// Need to set mongoDB variable to the uri for your own database

const user = ''
const password = ''
const mongoDB = `mongodb+srv://teachingStaff:cmsc388b@cluster0-fmejw.mongodb.net/LectureExample?retryWrites=true`;
mongoose.connect(mongoDB, { useNewUrlParser: true, useUnifiedTopology: true, useFindAndModify: false });
const dB = mongoose.connection;
dB.on('error', console.error.bind(console, 'MongoDB connection error:'));

app.get("/", function(req, res) {
  res.json({test: "response"});
});

module.exports = app;
