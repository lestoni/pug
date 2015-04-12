/**
 * Module dependencies.
 */
module.exports = function (mongoose) {
    var express = require('express'), mongoose = mongoose || require('mongoose'),
        rest = require('../index.js'), models = require('./models/user')(mongoose), compat = require('../lib/compat');

    var app = express();
    //Configuration

    app.use(compat.bodyParser());


    //map the search user to findUserLike
    app.get(/\/v1\/user\/search(.*)/, function (req, res, next) {
        //http://localhost:3001/blog/finder/search/J
        req.url = '/v1/user/finder/findUserLike/' + req.params[0];
        //req.query.transform = '_idToId';
        //req.query.populate = 'comments,author';
        next();

    })

    app.use('/v1', rest({ mongoose: mongoose}).rest())
    app.use(compat.errorHandler({ dumpExceptions: true, showStack: true }));
    return app;
}

