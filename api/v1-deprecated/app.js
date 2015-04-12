
var connection = require('mongoose').createConnection('mongodb://localhost/pug', function () {
    require('pow-mongoose-fixtures').load(__dirname + '/data', connection); //preload db with test data
    var app = require('./server')(connection),
        config=module.exports =require('./config/'+app.settings.env); //get the settings

    //turn on debug or not
    //require('mongoose').set();

    app.listen(config.http.port);

    console.log("PUG api listening on port %d in %s mode, visit http://localhost:3001/v1/", config.http.port, app.settings.env);
});


