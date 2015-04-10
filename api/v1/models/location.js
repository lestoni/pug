/**
 * Location model.
 * @param m
 * @returns {{Location: *}}
 */
module.exports = function (m) {
    var mongoose = m || require('mongoose'), Schema = m.base.Schema, CallbackQuery = require('../../lib/callback-query')
        , uniqueValidator = require('mongoose-unique-validator');

    //plugin the unique validator
    LocationSchema.plugin(uniqueValidator,{ message: 'Error, {PATH} {VALUE} is already taken.'});

    var LocationSchema = new Schema({
        title:{type:String,required:true,index:true},
        full_address:{type:String,default:null},
        location: {
            'type': {
                type: String,
                required: true,
                enum: ['Point', 'LineString', 'Polygon'],
                default: 'Point'
            },
            coordinates: [Number]
        },
        created_at: {type:Date,default:Date.now},
        updated_at: {type:Date,default:Date.now}
    });


    /**
     * @Todo implement 'geoNear' for queries on location
     * */

    /**
     * Some pre-save functions.
     * */
    LocationSchema.pre('save', function (next) {
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
    LocationSchema.statics.findLocationLike = function findLocationLike(q, title) {
        var search = title && title.length ? title.shift() : q && q.title;
        if (!search)
            return this.find({_id: null});

        return this.find({title: new RegExp(search, 'i')});
    }


    /**
     * This will be exposed as /v1/location/search/whatever
     * @param q
     * @param search term
     * @return query object
     */
    LocationSchema.statics.search = function(q, term) {
        var search = term && term.length ? term.shift() : q && q.term;
        if (!search)
            return this.find({_id: null});

        var regex = {$regex:new RegExp(search, 'i')}
        return this.find({}).or([
            {title:regex},
            {full_address:regex},
        ]);

    }

    /**
     * This will be exposed as /v1/location/nearby
     * @param point
     * @param distance
     * @return query object
     */
    LocationSchema.methods.findNearbyLocation = function findNearbyLocation(point, distance) {
     return this.geoNear(point, {maxDistance:distance, spherical: true}, function (err, data) {
        if(err) return err;
         res.send(data);
     });
     }




    /**
     * Shows how to create a raw mongodb query and use it within mers.  This
     * could also be used to use a non mongodb data source.
     * @param q
     * @return {Function}
     */
    LocationSchema.statics.findRaw = function onFindRaw(query$) {
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
    LocationSchema.statics.findByCallback = function onFindByCallback(query$id) {
        return this.find({_id: query$id}).exec();
    }


    //var User = mongoose.model('Location', LocationSchema);return Location;
    return {User: m.model('Location', LocationSchema)};
};