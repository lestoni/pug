/**
 * Created by opips on 4/12/15.
 */
/**
 * Add other custom end points specific to {{User Model}} here
 * @Todo {JohnAdamsy} figure out best way to have the model routes implemented here. Bypassing all controllers in the interim
 * */

var validateSearchTerm = function(req, res, next) {
    if (!req.body.username || !req.body.password)
        var response={
            status:400,
            code:1024,
            message:'Validation Failed',
            errors:[{
                "code":5402,
                "field":"password",
                "message":"Password cannot be blank"
            },
                {"code":5401,
                    "field":"username",
                    "message":"Username cannot be blank"}]
        };

        return next(response); // We can also throw an error from a before route
        req.body.username = req.body.username.toLowerCase();
    return next(); // Call the handler
},
    User = require('../models/user');

    var model =User.model.methods([
        'get',
        'post',
        'put',
        'delete'])
        .route('login', { //This should be exposed as /v1/user/login
        handler: function(req, res, next) {
           // User.Model.find({'pug_credentials.username': new RegExp(search, 'i')}, function(err, list) {
            User.Model.getAuthenticated(req.body.username,req.body.password,function(err, user, reason){
                if (err)
                    return next({ status: 500,code:1010,message:'Something went wrong on our side. Try again' }); // Error handling
                    var response={};
                    if(user){
                        //user is authenticated
                        res.status = 200;
                        console.log('login success');
                        // res.bundle is what is returned
                        res.bundle = user;
                        return next();
                    }else {
                        //user could not be logged in
                        console.log('login failed');
                        if (reason === reason.NOT_FOUND || reason === reason.PASSWORD_INCORRECT) {
                            response.push({
                                status: 400,
                                code: 1025,
                                message: 'Invalid Password/Username combination'
                            })
                        } else {
                            response.push({
                                status: 400,
                                code: 1026,
                                message: 'Account has been temporarily locked'
                            })
                        }
                        //respond back
                        return next(response);
                    }


            });
        },
        detail: false, // detail makes sure we have one model to work on, in this case we don't need from the client
            methods: ['post'] // only respond to GET requests via this end point
        })
        .before('post', noop)
        .after('post', noop)
        .before('put', noop)
        .after('put', noop)
        .before('login', validateSearchTerm) //before searching ensure we have a search item
        .after('athirdroute', after);

exports = module.exports = model;
