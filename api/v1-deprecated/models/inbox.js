/**
 * Inbox model.
 * @param m
 * @returns {{Inbox: *}}
 */
module.exports = function (m) {
    var mongoose = m || require('mongoose'), Schema = m.base.Schema, CallbackQuery = require('../../lib/callback-query')
        , uniqueValidator = require('mongoose-unique-validator');

    //plugin the unique validator
    InboxSchema.plugin(uniqueValidator,{ message: 'Error, {PATH} {VALUE} is already taken.'});

    var InboxSchema = new Schema({
        content:{type:String,default:'Wonna PUG now?'},
        from:[{ type: Schema.ObjectId, ref: 'Member' }],
        recipient:[{ type: Schema.ObjectId, ref: 'Member' }],
        is_read:{type:Boolean,default:false},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    });


    /**
     * @Todo implement team_icon upload handler in a controller and any other custom endpoint
     * */

    /**
     * Some pre-save functions.
     * */
    InboxSchema.pre('save', function (next) {
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
     * This will be exposed as /v1/inbox/message/search
     * @param q
     * @param search name
     */
    InboxSchema.statics.findInboxLike = function findInboxLike(q, content) {
        var search = content && content.length ? content.shift() : q && q.name;
        if (!search)
            return this.find({_id: null});

        return this.find({content: new RegExp(search, 'i')});
    }


    /**
     * This will be exposed as /v1/inbox/search/whatever
     * @param q
     * @param search term
     * @return query object
     */
    InboxSchema.statics.search = function(q, term) {
        var search = term && term.length ? term.shift() : q && q.term;
        if (!search)
            return this.find({_id: null});

        var regex = {$regex:new RegExp(search, 'i')}
        return this.find({}).or([
            {recipient:regex},
            {from:regex},
            {content:regex}
        ]);

    }



    /**
     * Shows how to create a raw mongodb query and use it within mers.  This
     * could also be used to use a non mongodb data source.
     * @param q
     * @return {Function}
     */
    InboxSchema.statics.findRaw = function onFindRaw(query$) {
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
    InboxSchema.statics.findByCallback = function onFindByCallback(query$id) {
        return this.find({_id: query$id}).exec();
    }


    //var Inbox = mongoose.model('Inbox', InboxSchema);return Inbox;
    return {Inbox: m.model('Inbox', InboxSchema)};
};