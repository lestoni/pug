/**
 * Created by opips on 4/12/15.
 */
var express = require('express'),
    mongoose = require('mongoose'),
    app = module.exports = express(),
    bodyParser=require('body-parser'),
    multer = require('multer'),
    moment = require('moment'),
    user=require('../models/user'),
    config=require('../config/'+app.get('env'));

app.use(bodyParser.json()); // parse application/json
app.use(bodyParser.urlencoded({ extended: true })); // parse application/x-www-form-urlencoded
app.use(multer());// parse multipart/form-data
app.use(express.query());
//app.set('view engine', 'jade');

app.mongoose = mongoose; // used for testing

var connection=mongoose.connect(config.db); //connect to mongo

require('pow-mongoose-fixtures').load('../data', connection); //preload db with test data

//register all models here for now
/**
 * Todo JohnAdamsy ;1. better versioning method. 2. Also create modular routes for each available model 3. Custom search other than than the available filters
 * */
user.register(app, '/v1/user');

if (!module.parent) {
    //app.listen(config.http.port);
    app.listen(config.http.port, function(){
        console.log(moment().format('ddd')+' '+moment().format()+' SYSTEM:-> PUG api listening in %s mode  on port %d', app.get('env'),config.http.port);
    });
}