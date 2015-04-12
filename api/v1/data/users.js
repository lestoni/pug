
/**
 * Just some dummy data to 3 create test users on startup
 * */
var _id = require('mongoose').Types.ObjectId;

exports.User = [
    {
        user_type: 'test-player',
        pug_credentials: {username:'kali',password:'secret123'},
        oauth_credentials:{facebook:{id:null,token:null},twitter:{id:null,token:null},google:{id:null,token:null}},
        account_rescue:{reset_id:null,expires_on:{type:Date}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    },
    {
        user_type: 'test-player',
        pug_credentials: {username:'kobe',password:'secret121'},
        oauth_credentials:{facebook:{id:null,token:null},twitter:{id:null,token:null},google:{id:null,token:null}},
        account_rescue:{reset_id:null,expires_on:{type:Date}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    },
    {
        user_type: 'test-player',
        pug_credentials: {username:'lebron',password:'secret111'},
        oauth_credentials:{facebook:{id:null,token:null},twitter:{id:null,token:null},google:{id:null,token:null}},
        account_rescue:{reset_id:null,expires_on:{type:Date}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    }


];
