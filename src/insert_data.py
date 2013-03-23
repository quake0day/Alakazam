# insert new data
post1 = {"Name":"piers","lat":"43.00283408332881","lng":"-78.7872705197334","date":datetime.datetime.utcnow()}

post2 = {"Name":"piers","lat":"43.0028772374798","lng":"-78.78690305709836","date":datetime.datetime.utcnow()}

post_id = posts.insert(post1)
post_id = posts.insert(post2)