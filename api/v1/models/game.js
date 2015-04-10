/**
 * Game model.
 * @param m
 * @returns {{Game: *}}
 */
module.exports = function (m) {
    var mongoose = m || require('mongoose'), Schema = m.base.Schema, CallbackQuery = require('../../lib/callback-query')
        , uniqueValidator = require('mongoose-unique-validator');

    var bio_max_length = [160, 'The value of `{PATH}` (`{VALUE}`) exceeds the maximum allowed ({MAXLENGTH}) characters.']; //custom validator

    var GameSchema = new Schema({
        location:[{ type: Schema.ObjectId, ref: 'Location' }],
        game_title:{type:String,required:true,index:true},
        scheduled_date:{type:Date,required:true,default:Date.now},
        start_time:{type:String,required:true,default:'00:00'},
        duration:{type:Number,required:true,enum:[3,5,7,10,15],default:15},
        status:{type:String,enum:['Open', 'In Session', 'Done', 'Postponed', 'Closed', 'Cancelled'],default:'Open'},
        comments:[{comment_by:[{ type: Schema.ObjectId, ref: 'Member' }],message:{type:String,default:null},created_at:{type:Date,default:Date.now}}],
        game_mode:{type:String,enum:['1 Aside','2 Aside','3 Aside','4 Aside','5 Aside',null],default:null},
        scores:{team_a:{type:Number,default:null},team_b:{type:Number,default:null}},
        entry_fee:{type:Number,default:0},
        players:{max:{type:Number,default:2},joined:[{ type: Schema.ObjectId, ref: 'Member' }]},
        teams:{team_a:[{ type: Schema.ObjectId, ref: 'Team' }],team_b:[{ type: Schema.ObjectId, ref: 'Team' }]},
        created_by:[{ type: Schema.ObjectId, ref: 'User' }],
        rating:{poor:{type:Number,default:0},medium:{type:Number,default:0},good:{type:Number,default:0},great:{type:Number,default:0}},
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    });

    //plugin the unique validator
    GameSchema.plugin(uniqueValidator,{ message: 'Error, {PATH} {VALUE} is already taken.'});

    /**
     * @Todo implement validation: team_a should always be different from team_b
     * */

    /**
     * Some pre-save functions. E.g validate bio length, email address, dob, ...etc
     * */
    GameSchema.pre('save', function (next) {
        var game=this;

        //ensure team_a is not the same as team_b
        game.validate(function (err) {
            console.log(String(err)) ;
        })


    });


    /**
     * Some post-save functions
     * */


    /**
     * This will be exposed as /v1/game/status/open
     * @param q
     * @param search term
     */
    GameSchema.statics.findOpenGame = function findOpenGame(q, status) {
        var search = status && status.length ? status.shift() : q && q.status;
        if (!search)
            return this.find({_id: null});

        return this.find({status: new RegExp(search, 'i')});
    }


    /**
     * This will be exposed as /v1/game/search/whatever
     * @param q
     * @param search term
     * @return query object
     */
    GameSchema.statics.search = function(q, term) {
        var search = term && term.length ? term.shift() : q && q.term;
        if (!search)
            return this.find({_id: null});

        var regex = {$regex:new RegExp(search, 'i')}
        return this.find({}).or([
            {duration:regex},
            {status:regex},
            {game_title:regex},
            {entry_fee:regex},
            {location:regex},
            {scheduled_date:regex}
        ]);

    }



    /**
     * Shows how to create a raw mongodb query and use it within mers.  This
     * could also be used to use a non mongodb data source.
     * @param q
     * @return {Function}
     */
    GameSchema.statics.findRaw = function onFindRaw(query$) {
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
    GameSchema.statics.findByCallback = function onFindByCallback(query$id) {
        return this.find({_id: query$id}).exec();
    }


    //var Game = mongoose.model('Game', GameSchema);return Game;
    return {Game: m.model('Game', GameSchema)};
};