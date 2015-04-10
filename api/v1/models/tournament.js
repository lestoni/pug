/**
 * Tournament model.
 * @param m
 * @returns {{Tournament: *}}
 */
module.exports = function (m) {
    var mongoose = m || require('mongoose'), Schema = m.base.Schema, CallbackQuery = require('../../lib/callback-query')
        , uniqueValidator = require('mongoose-unique-validator');

    var max_length = [160, 'The value of `{PATH}` (`{VALUE}`) exceeds the maximum allowed ({MAXLENGTH}) characters.']; //custom validator

    var TournamentSchema = new Schema({
        location:[{ type: Schema.ObjectId, ref: 'Location' }],
        title:{type:String,required:true,index:true},
        description:{type:String,default:null},
        start_date:{type:Date,required:true,default:Date.now},
        end_date:{type:String,required:true,default:'00:00'},
        status:{type:String,enum:['Open', 'In Session', 'Done', 'Postponed', 'Closed', 'Cancelled'],default:'Open'},
        games:[[{ type: Schema.ObjectId, ref: 'Game' }]],
        team_count:{type:Number,default:0},
        comments:[{comment_by:[{ type: Schema.ObjectId, ref: 'Member' }],message:{type:String,default:null},created_at:{type:Date,default:Date.now}}],
        created_by:[{ type: Schema.ObjectId, ref: 'User' }],
        rating:{poor:{type:Number,default:0},medium:{type:Number,default:0},good:{type:Number,default:0},great:{type:Number,default:0}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    });

    //plugin the unique validator
    TournamentSchema.plugin(uniqueValidator,{ message: 'Error, {PATH} {VALUE} is already taken.'});

    /**
     * @Todo implement validation and any other custom end points
     * */

    /**
     * Some pre-save functions. E.g validate bio length, email address, dob, ...etc
     * */
    TournamentSchema.pre('save', function (next) {
        var Tournament=this;

        //ensure team_a is not the same as team_b
        Tournament.validate(function (err) {
            console.log(String(err)) ;
        })


    });


    /**
     * Some post-save functions
     * */


    /**
     * This will be exposed as /v1/tournament/status/open
     * @param q
     * @param search term
     */
    TournamentSchema.statics.findOpenTournament = function findOpenTournament(q, status) {
        var search = status && status.length ? status.shift() : q && q.status;
        if (!search)
            return this.find({_id: null});

        return this.find({status: new RegExp(search, 'i')});
    }


    /**
     * This will be exposed as /v1/tournament/search/whatever
     * @param q
     * @param search term
     * @return query object
     */
    TournamentSchema.statics.search = function(q, term) {
        var search = term && term.length ? term.shift() : q && q.term;
        if (!search)
            return this.find({_id: null});

        var regex = {$regex:new RegExp(search, 'i')}
        return this.find({}).or([
            {start_date:regex},
            {status:regex},
            {title:regex},
            {location:regex}
        ]);

    }

    /**
     * Shows how to create a raw mongodb query and use it within mers.  This
     * could also be used to use a non mongodb data source.
     * @param q
     * @return {Function}
     */
    TournamentSchema.statics.findRaw = function onFindRaw(query$) {
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
    TournamentSchema.statics.findByCallback = function onFindByCallback(query$id) {
        return this.find({_id: query$id}).exec();
    }


    //var Tournament = mongoose.model('Tournament', TournamentSchema);return Tournament;
    return {Tournament: m.model('Tournament', TournamentSchema)};
};