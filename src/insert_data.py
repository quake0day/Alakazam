import datetime
from pymongo import MongoClient
#from time import sleep


def init_Mongo():
    client = MongoClient()
    db = client.test_database
    #collection = db.test_collection
    #posts = db.posts
    return db

def insert_data(lat,lng,activity,flag):
    db = init_Mongo()
    posts = db.posts
    post = {"Name":"piers","lat":lat,"lng":lng,"date":datetime.datetime.utcnow(),"activity":activity,"flag":flag}
    posts.insert(post)
    return True

def update_data(activity):
    db = init_Mongo()
    posts = db.posts
    data = list(posts.find().sort([ ('date', -1)]))
    #print data
    pid = data[0]['_id']
    name = data[0]['Name']
    lat = data[0]['lat']
    lng = data[0]['lng']
    flag = data[0]['flag']
    posts.update({"_id": pid}, {'Name':name, 'lat':lat, 'lng':lng, 'date':datetime.datetime.utcnow(), 'activity': activity, 'flag':flag} ) 
    print "DONE"

    #posts.update()
# insert new data
#lat = sys.argv[1]
#lng = sys.argv[2]
#print lat,lng
    #post1 = {"Name":"piers","lat":lat,"lng":lng,"date":datetime.datetime.utcnow(),"activity":"Walking"}

#post2 = {"Name":"piers","lat":"43.0028772374798","lng":"-78.78690305709836","date":datetime.datetime.utcnow()}

#post_id = posts.insert(post2)
#update_data("sleep")
#sleep(5)
#post_id = posts.insert(post1)
