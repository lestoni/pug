var _id = require('mongoose').Types.ObjectId;

exports.User = [
    {
        type: 'test-player',
        pug_credentials: {username:'lebron',password:'secret123'},
        oauth_credentials:{facebook:{id:null,token:null},twitter:{id:null,token:null},google:{id:null,token:null}},
        account_rescue:{reset_id:null,expires_on:{type:Date}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    },
    {
        type: 'test-player',
        pug_credentials: {username:'kobe',password:'secret121'},
        oauth_credentials:{facebook:{id:null,token:null},twitter:{id:null,token:null},google:{id:null,token:null}},
        account_rescue:{reset_id:null,expires_on:{type:Date}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    },
    {
        type: 'test-player',
        pug_credentials: {username:'kali',password:'secret111'},
        oauth_credentials:{facebook:{id:null,token:null},twitter:{id:null,token:null},google:{id:null,token:null}},
        account_rescue:{reset_id:null,expires_on:{type:Date}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    }


];
