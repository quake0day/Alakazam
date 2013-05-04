import web
import query
from knn import *
import json
from loadjson import *
urls = (
    '/', 'index',
    '/loc', 'loc',
    '/wifi', 'wifi',
    '/submit', 'submit'
)



CONVERT_MATRIX = [[43.002486,-78.787595],
[43.002684,-78.7877540],
[43.0028552758727,-78.78761932253838],
[43.002957276509164,-78.7876608967781],
[43.00261694680236,-78.78770783543587],
[43.00242863675962,-78.78764480352402]]

class index:
    def GET(self):
        tmpl = web.template.render("tmpl/")
        return tmpl.index()

    def POST(self):
        data = web.data()
        if data.split(",")[0].split("=")[0] == "ACCELEROMETER":
        #print data.split(",")[0].split("=")[0]
            print "_________________"
            print data.split(",")[0].split("=")[1].split("%2C")
            print "_________________"
        return "HIHI"


class wifi:
    def GET(self):
        return "GET WIFI"

    def POST(self):
        i = web.input()
        data = None
        try:
            wifiRes_json = i.wifiRes
            #print i.wifiRes
            data = json.loads(str(wifiRes_json))
        except Exception, e:
            data = None
            raise e
        if data is not None:
            data_mac_addr_n = []
            data_mac_addr, data_address_addr = init_mac_addr()
            bssid_list_len, bssid_list = mother_list_gen(data_mac_addr)
            group = matrix_generate(bssid_list_len, bssid_list, data_mac_addr)
            #print data['pointArray']
            data_mac_addr_n.append(data['pointArray'])
            returnMat = matrix_generate(bssid_list_len, bssid_list, data_mac_addr_n)
            labels = return_label(data_address_addr)
            #print returnMat
<<<<<<< HEAD
            print classify0(returnMat[0], group, labels, 3)
=======
            x,y = classify0(returnMat[0], group, labels, 3) 
            print x
            print y
            #insert_data(lat,lng,activity)
>>>>>>> 91b2c45f1420f9d92f1c0825986aaabff3ab6b85
        return "Done"


class loc:
    def GET(self):
        db = query.init_Mongo()
        return query.query_one_loc(db)

    def POST(self):
        data = web.data()

        print data
        return "This post:)"


class submit:

    def POST(self):
        return "This post"

if __name__ == "__main__":
    app = web.application(urls, globals())
    app.run()
