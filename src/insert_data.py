import pymongo
import datetime
from pymongo import MongoClient
from time import sleep
import sys


def init_Mongo():
    client = MongoClient()
    db = client.test_database
    collection = db.test_collection
    #posts = db.posts
    return db
    
db = init_Mongo()
posts = db.posts

# insert new data
lat = sys.argv[1]
lng = sys.argv[2]
print lat,lng
post1 = {"Name":"piers","lat":lat,"lng":lng,"date":datetime.datetime.utcnow()}

#post2 = {"Name":"piers","lat":"43.0028772374798","lng":"-78.78690305709836","date":datetime.datetime.utcnow()}

#post_id = posts.insert(post2)

#sleep(5)
post_id = posts.insert(post1)
