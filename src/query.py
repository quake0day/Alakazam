from pymongo import MongoClient


def init_Mongo():
    client = MongoClient()
    db = client.test_database
    #collection = db.test_collection
    #posts = db.posts
    return db


def query_post(db):
    posts = db.posts
    print posts.find_one()
    print posts.count()
    return posts.find_one()


def query_one_loc(db):
    posts = db.posts
    # sort the result
    data = posts.find().sort("date", -1)
    # take the newest result
    name = data[0]["Name"]
    lat = float(data[0]["lat"])
    lng = float(data[0]["lng"])
    date = data[0]["date"]
    #print lat
    #print lng
    #print "[{lat:"+lat+",lng:"+lng+"}]"
    activity = data[0]["activity"]
    flag = data[0]['flag']
    #print lat
    #print lng
    #print "[{lat:"+lat+",lng:"+lng+"}]"
    return lat, lng, name, date, activity,flag


# --- FOR TEST ONLY ---
#db = init_Mongo()
#query_one_loc(db)
#query_post(db)
