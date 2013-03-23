import pymongo
import datetime
from pymongo import MongoClient


def init_Mongo():
    client = MongoClient()
    db = client.test_database
    collection = db.test_collection
    #posts = db.posts
    return db

def query_post(db):
    posts = db.posts
    print posts.find_one()
    print posts.count()
    return posts.find_one()


# --- FOR TEST ONLY ---
#db = init_Mongo()
#query_post(db)
