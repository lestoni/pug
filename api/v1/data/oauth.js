var _id = require('mongoose').Types.ObjectId;

exports.User = [
    {
        user_type: 'test-player',
        pug_credentials: {username:'kAli',password:'secret123'},
        oauth_credentials:{facebook:{id:null,token:null},twitter:{id:null,token:null},google:{id:null,token:null}},
        account_rescue:{reset_id:null,expires_on:{type:Date}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    },
    {
        user_type: 'test-player',
        pug_credentials: {username:'KObe',password:'secret121'},
        oauth_credentials:{facebook:{id:null,token:null},twitter:{id:null,token:null},google:{id:null,token:null}},
        account_rescue:{reset_id:null,expires_on:{type:Date}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    },
    {
        user_type: 'test-player',
        pug_credentials: {username:'leBRon',password:'secret111'},
        oauth_credentials:{facebook:{id:null,token:null},twitter:{id:null,token:null},google:{id:null,token:null}},
        account_rescue:{reset_id:null,expires_on:{type:Date}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    }


];
