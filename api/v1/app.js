
var connection = require('mongoose').createConnection('mongodb://localhost/pug', function () {
    require('pow-mongoose-fixtures').load(__dirname + '/data', connection);
    var app = require('./server')(connection);

    app.listen(3001);

    console.log("PUG api listening on port 3001 in %s mode, visit http://localhost:3001/v1/", app.settings.env);
});


