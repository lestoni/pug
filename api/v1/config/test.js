/**
 * Created by opips on 4/11/15.
 */
'use strict';
module.exports={
    db:'mongodb://localhost/pug-test',
    mongoose: {
        debug: false
    },
    http:{
        port:3002
    },
    facebook: {
        clientID: 'DEFAULT_APP_ID',
        clientSecret: 'APP_SECRET',
        callbackURL: 'http://localhost:3002/auth/facebook/callback'
    },
    twitter: {
        clientID: 'DEFAULT_CONSUMER_KEY',
        clientSecret: 'CONSUMER_SECRET',
        callbackURL: 'http://localhost:3002/auth/twitter/callback'
    },
    google: {
        clientID: 'DEFAULT_APP_ID',
        clientSecret: 'APP_SECRET',
        callbackURL: 'http://localhost:3002/auth/google/callback'
    },
    emailFrom: 'SENDER EMAIL ADDRESS', // sender address like ABC <abc@example.com>
    mailer: {
        service: 'SERVICE_PROVIDER', // Gmail, SMTP
        auth: {
            user: 'EMAIL_ID',
            pass: 'PASSWORD'
        }
    }
};
