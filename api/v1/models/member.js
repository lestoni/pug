/**
 * Member model.
 * @param m
 * @returns {{Member: *}}
 */
module.exports = function (m) {
    var mongoose = m || require('mongoose'), Schema = m.base.Schema, CallbackQuery = require('../../lib/callback-query')
        , uniqueValidator = require('mongoose-unique-validator');

    var bio_max_length = [160, 'The value of `{PATH}` (`{VALUE}`) exceeds the maximum allowed ({MAXLENGTH}) characters.']; //custom validator

    var MemberSchema = new Schema({
        user:[{ type: Schema.ObjectId, ref: 'User' }], //each member has 1 user account
        account_name:{type:String,required:true,index:true}, //your alias or display name...super editable.
        email:{type:String,required:true,index:true,unique: true,lowercase:true}, //add email validation later
        full_name:{type:String,default:null},
        dob:{type:Date,default:'01/01/1970'},
        gender:{type:String,default:null},
        player:{skills:{type:Array,default:null},games_played:{type:Number,default:0},default_position:{type:String,default:null},
            ball_handling:{type:String,default:null},height:{type:String,default:null},weight:{type:Number,default:null}},
        bio:{type:String,default:null,maxlength:bio_max_length},//max of 160 characters
    city:{type:String,default:null},
    state:{type:String,default:null},
    profile_avatar:{type:String,default:null},
    profile_extra:{followers:{type:Array,default:[]},following:{type:Array,default:[]}},
    rating:{poor:{type:Number,default:0},medium:{type:Number,default:0},good:{type:Number,default:0},great:{type:Number,default:0}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    });

    //plugin the unique validator
    MemberSchema.plugin(uniqueValidator,{ message: 'Error, {PATH} {VALUE} is already taken.'});

    /**
     * @Todo implement upload profile_avatar handler in a controller
     * */

    /**
     * Some pre-save functions. E.g validate bio length, email address, dob, ...etc
     * */
    MemberSchema.pre('save', function (next) {
        var member=this;

        //check the bio length
        member.validate(function (err) {
            console.log(String(err)) ;
        })


    });


    /**
     * Some post-save functions
     * */


    /**
     * Note this must return a query object.
     * @param q
     * @param search term
     */
    MemberSchema.statics.findMemberLike = function findMemberLike(q, username) {
        var search = username && username.length ? username.shift() : q && q.username;
        if (!search)
            return this.find({_id: null});

        return this.find({'pug_credentials.username': new RegExp(search, 'i')});
    }


    /**
     * This will be exposed as /v1/member/search/whatever
     * @param q
     * @param search term
     * @return query object
     */
    MemberSchema.statics.search = function(q, term) {
        var search = term && term.length ? term.shift() : q && q.term;
        if (!search)
            return this.find({_id: null});

        var regex = {$regex:new RegExp(search, 'i')}
        return this.find({}).or([
            {city:regex},
            {state:regex},
            {full_name:regex},
            {account_name:regex},
            {user:regex},
            {bio:regex}
        ]);

    }



    /**
     * Shows how to create a raw mongodb query and use it within mers.  This
     * could also be used to use a non mongodb data source.
     * @param q
     * @return {Function}
     */
    MemberSchema.statics.findRaw = function onFindRaw(query$) {
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
    MemberSchema.statics.findByCallback = function onFindByCallback(query$id) {
        return this.find({_id: query$id}).exec();
    }


    //var User = mongoose.model('User', MemberSchema);return User;
    return {User: m.model('Member', MemberSchema)};
};