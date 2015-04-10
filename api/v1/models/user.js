/**
 * User model. Wrap the export so that a mongoose can be passed in.  This is useful, for testing, and managing connections.
 * @param m
 * @returns {{User: *}}
 */
module.exports = function (m) {
    var mongoose = m || require('mongoose'), Schema = m.base.Schema, CallbackQuery = require('../../lib/callback-query')
        , uniqueValidator = require('mongoose-unique-validator'),bcrypt = require('bcrypt'),
        SALT_WORK_FACTOR = 10;


    var UserSchema = new Schema({
        user_type:{type:String,default:'player'},
        pug_credentials: {username:{type:String,required:true,index:true,unique: true },password:{type:String,required:true}},
        oauth_credentials:{facebook:{id:{type:String,default:null},token:{type:String,default:null}},twitter:{id:{type:String,default:null},token:{type:String,default:null}},google:{id:{type:String,default:null},token:{type:String,default:null}}},
        account_rescue:{reset_id:{type:String,default:null},expires_on:{type:Date,default:null}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    });

    //plugin the unique validator
    UserSchema.plugin(uniqueValidator,{ message: 'Error, {PATH} {VALUE} is already taken.'});

    /**
     * Some pre-save functions. E.g hash password,set password reset token expiry datetime, compare passwords
     * */
    UserSchema.pre('save', function (next) {
        var user=this;
        //change the username to lower case
        user.pug_credentials.username=user.pug_credentials.username.toLowerCase();
       // next();

// only hash the password if it has been modified (or is new)
        if (!user.isModified('pug_credentials.password')) return next();

// generate a salt
        bcrypt.genSalt(SALT_WORK_FACTOR, function(err, salt) {
            if (err) return next(err);

            // hash the password using our new salt
            bcrypt.hash(user.pug_credentials.password, salt, function(err, hash) {
                if (err) return next(err);

                // override the clear text password with the hashed one
                user.pug_credentials.password = hash;
                next();
            });
        });


    });


    /**
     * Some post-save functions
     * */


    /**
     * Note this must return a query object.
     * @param q
     * @param username
     */
    UserSchema.statics.findUserLike = function findUserLike(q, username) {
        var search = username && username.length ? username.shift() : q && q.username;
        if (!search)
            return this.find({_id: null});

        return this.find({'pug_credentials.username': new RegExp(search, 'i')});
    }

    /*UserSchema.methods.findCommentsLike = function (q, term) {
        var search = term || q.title;
        return this.find({comments: new RegExp(search, 'i')});
    }*/

    //compare a password. Useful for user login with a pug password
    UserSchema.methods.comparePassword = function(candidatePassword, cb) {
        bcrypt.compare(candidatePassword, this.pug_credentials.password, function(err, isMatch) {
            if (err) return cb(err);
            cb(null, isMatch);
        });
    };



    /**
     * Shows how to create a raw mongodb query and use it within mers.  This
     * could also be used to use a non mongodb data source.
     * @param q
     * @return {Function}
     */
    UserSchema.statics.findRaw = function onFindRaw(query$) {
        var collection = this.collection;
        return new CallbackQuery(function (cb) {
            collection.find(function (err, cursor) {
                if (err) return cb(err);
                cursor.toArray(function (err, docs) {
                    cb(err, docs);
                });
            });

        });
    }
    UserSchema.statics.findByCallback = function onFindByCallback(query$id) {
        return this.find({_id: query$id}).exec();
    }
    /**
     * This is just an example, if this proves useful, may make it part of mers.
     * @param q
     * @param collection
     * @constructor
     */

    //var User = mongoose.model('User', UserSchema);return User;
    return {User: m.model('User', UserSchema)};
};