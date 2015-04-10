/**
 * Notification model.
 * @param m
 * @returns {{Notification: *}}
 */
module.exports = function (m) {
    var mongoose = m || require('mongoose'), Schema = m.base.Schema, CallbackQuery = require('../../lib/callback-query')
        , uniqueValidator = require('mongoose-unique-validator');

    //plugin the unique validator
    NotificationSchema.plugin(uniqueValidator,{ message: 'Error, {PATH} {VALUE} is already taken.'});

    var NotificationSchema = new Schema({
        title:{type:String,default:'New game created'},
        type:{ type:String, enum:['Game','Follower','Following','Tournament','Rating'],default: 'Game' },
        payload:{type:Array,default:[]},
        target:[{who:[{ type: Schema.ObjectId, ref: 'User' }],is_read:{type:Boolean,default:false}}],
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    });


    /**
     * @Todo implement team_icon upload handler in a controller and any other custom endpoint
     * */

    /**
     * Some pre-save functions.
     * */
    NotificationSchema.pre('save', function (next) {
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
     * This will be exposed as /v1/member/:id/notification/unread
     * @param id
     * @param status
     */
    NotificationSchema.statics.findUnreadNotification = function findUnreadNotification(id, status) {
        var search = id && id.length ? id.shift() : id && id.status;
        if (!search)
            return this.find({_id: null});

        return this.find({'target.who': id,'target.is_read':status});
    }


    /**
     * This will be exposed as /v1/notification/search/whatever
     * @param q
     * @param search term
     * @return query object
     */
    NotificationSchema.statics.search = function(q, term) {
        var search = term && term.length ? term.shift() : q && q.term;
        if (!search)
            return this.find({_id: null});

        var regex = {$regex:new RegExp(search, 'i')}
        return this.find({}).or([
            {title:regex},
            {created_at:regex}
        ]);

    }



    /**
     * Shows how to create a raw mongodb query and use it within mers.  This
     * could also be used to use a non mongodb data source.
     * @param q
     * @return {Function}
     */
    NotificationSchema.statics.findRaw = function onFindRaw(query$) {
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
    NotificationSchema.statics.findByCallback = function onFindByCallback(query$id) {
        return this.find({_id: query$id}).exec();
    }


    //var Notification = mongoose.model('Notification', NotificationSchema);return Notification;
    return {Notification: m.model('Notification', NotificationSchema)};
};