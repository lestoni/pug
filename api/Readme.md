#PUG api powered by node express and mongo. 

###Author
@JohnAdamsy for Coders4Africa

###libs
node-restful written by @baugarten

### Deployment??

Wiring up the boxes on Google cloud as I speak..oh as I type this. Then will point to [Service Point] (http://api.mypug.me)

### Remaining Pieces to patch up eventually
1. Caching layer (that's a beast!, I hope not :D )
2. Oauth Security for all endpoints (another manageable beast. This will also separate authentication data from the API's consumed resources)
3. Serve via HTTPS
4. Hook to monitoring services such as new Relic, Paperptrail, StackDriver etc



Not the final documentation.
=========================

Sticking to [RESTful APIs best practices] (http://www.vinaysahni.com/best-practices-for-a-pragmatic-restful) all the way. [@Amadou] (https://github.com/maxosba) thanks for the link again, can't say it enough.

Lifted from [baugarten's](https://github.com/baugarten) [repo] (https://github.com/baugarten/node-restful). 
Which I hope will get time to contribute to as well. There are some few needs :)

Also keeping an eye on [node-restify] (https://github.com/mcavage/node-restify) by Mc Cavage. I find it stunning regardless of some [issues] (https://raygun.io/blog/2015/03/node-performance-hapi-express-js-restify/) which I think [they worked on] (https://github.com/mcavage/node-restify/tree/master/examples/bench#results)
and [also given HUGE!! credit by folks at New Relic] (http://blog.newrelic.com/2014/08/15/node-js-frameworks-hapi-js-restify-geddy/)

Final documentation for [PUG API] (https://github.com/C4AProjects/pug/tree/master/api) will be available on [pug's apiary] (http://docs.pug.apiary.io/#)

```

CRUD Framework:

    GET /resource
    GET /resource/:id
    POST /resource
    PUT /resource/:id
    DELETE /resource/:id
    

#### Selecting the entity-properties you need


A `GET` request to `/user/?select=pug_credentials.username%20user_type` will result in:

```json
[
    {
            "_id": "552a973427b49a335be09d2b",
            "pug_credentials": {
                "username": "kobe"
            },
            "user_type": "test-player"
        },
        {
            "_id": "552a973427b49a335be09d2a",
            "pug_credentials": {
                "username": "kali"
            },
            "user_type": "test-player"
        },
        {
            "_id": "552a973427b49a335be09d2c",
            "pug_credentials": {
                "username": "lebron"
            },
            "user_type": "test-player"
        }
]
```

#### Limiting the number and skipping items

Use `skip` and `limit` filters for your pagination heavy lifting 

`/user/?limit=2` will give you the first 2 items  
`/user/?skip=3` will skip the first 3 and give you the rest  
`/user/?limit=2&skip=3` will skip the first 3 and then give you the second 2

#### Sorting the result

Use a `sort` querystring parameter with the property you want to sort on. `/user/?sort=pug_credentials.username` will give you a list sorted on the `pug_credentials.username` property, with an ascending sort order by default.

### To change the sort order? 
Add a MINUS before your value: `/user/?sort=-updated_at` Sorts the results  by the `updated_at` in DESC order  
Add a PLUS before your value: `/user/?sort=+updated_at` Sorts the results  by the `updated_at` in ASC order

#### Filtering the results

Not really tested this now. But will seek to improve it with friendlier search keywords :).
 But the idea is that you can ask the service for equality, or values greater or less than, give it an array of values it should match to, or even a regex.

| Filter                       | Query  | Example                                              | Description                     |
|------------------------------|--------|------------------------------------------------------|---------------------------------|
| **equal**                    | `equals` | `/user?gender=male` or `/users?gender__equals=male` | both return all male users      |
| **not equal**                | `ne`     | `/user?gender__ne=male`                             | returns all users who are not male (`female` and `x`)        |
| **greater than**             | `gt`     | `/user?age__gt=18`                                  | returns all users older than 18                                   |
| **greater than or equal to** | `gte`    | `/user?age__gte=18`                                 | returns all users 18 and older (age should be a number property) |
| **less than**                | `lt`     | `/user?age__lt=30`                                  | returns all users age 29 and younger                              |
| **less than or equal to**    | `lte`    | `/user?age__lte=30`                                 | returns all users age 30 and younger                             |
| **in**                       | `in`     | `/user?gender__in=female,male`                         | returns all female and male users                    |
| **Regex**                    | `regex`  | `/users?username__regex=/^baugarten/i` | returns all users with a username starting with baugarten           |

### Populating a sub-entity

When you have setup a mongoose Schema with properties referencing other entities, you can ask the service to populate them for you.

A `GET` request to `/users/542edff9fffc55dd29d99346` will result in:

```json
{
    "_id": "542edff9fffc55dd29d99346",
    "name": "the employee",
    "email": "employee@company.com",
    "boss": "542edff9fffc55dd29d99343",
    "__v": 0
}
```
A `GET` request to `/users/542edff9fffc55dd29d99346?populate=boss` will result in:

```json
{
    "_id": "542edff9fffc55dd29d99346",
    "name": "the employee",
    "email": "employee@company.com",
    "boss": {
        "_id": "542edff9fffc55dd29d99343",
        "name": "the boss",
        "email": "boss@company.com",
        "__v": 0
    },
    "__v": 0
}
```




