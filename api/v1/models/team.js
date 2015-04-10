/**
 * Team model.
 * @param m
 * @returns {{Team: *}}
 */
module.exports = function (m) {
    var mongoose = m || require('mongoose'), Schema = m.base.Schema, CallbackQuery = require('../../lib/callback-query')
        , uniqueValidator = require('mongoose-unique-validator');

    //plugin the unique validator
    TeamSchema.plugin(uniqueValidator,{ message: 'Error, {PATH} {VALUE} is already taken.'});

    var TeamSchema = new Schema({
        name:{type:String,required:true,unique:true,index:true},
        team_icon:{type:String,default:null},
        games_played:{type:Number,default:0},
        players:[{ type: Schema.ObjectId, ref: 'Member' }],
        created_by:[{ type: Schema.ObjectId, ref: 'User' }],
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    });


    /**
     * @Todo implement team_icon upload handler in a controller and any other custom endpoint
     * */

    /**
     * Some pre-save functions.
     * */
    TeamSchema.pre('save', function (next) {
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
     * @param search name
     */
    TeamSchema.statics.findTeamLike = function findTeamLike(q, name) {
        var search = name && name.length ? name.shift() : q && q.name;
        if (!search)
            return this.find({_id: null});

        return this.find({name: new RegExp(search, 'i')});
    }


    /**
     * This will be exposed as /v1/team/search/whatever
     * @param q
     * @param search term
     * @return query object
     */
    TeamSchema.statics.search = function(q, term) {
        var search = term && term.length ? term.shift() : q && q.term;
        if (!search)
            return this.find({_id: null});

        var regex = {$regex:new RegExp(search, 'i')}
        return this.find({}).or([
            {name:regex},
            {created_by:regex}
        ]);

    }



    /**
     * Shows how to create a raw mongodb query and use it within mers.  This
     * could also be used to use a non mongodb data source.
     * @param q
     * @return {Function}
     */
    TeamSchema.statics.findRaw = function onFindRaw(query$) {
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
    TeamSchema.statics.findByCallback = function onFindByCallback(query$id) {
        return this.find({_id: query$id}).exec();
    }


    //var User = mongoose.model('Team', TeamSchema);return Team;
    return {User: m.model('Team', TeamSchema)};
};