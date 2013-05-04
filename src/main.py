import web
import query
from knn import *
import json
from loadjson import *
urls=(
        '/','index',
        '/loc','loc',
        '/wifi','wifi',
        '/submit','submit'
        )

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
        except Exception,e:
            data = None
            raise e
        if data != None:
            data_mac_addr_n = []
            data_mac_addr, data_address_addr = init_mac_addr()
            bssid_list_len, bssid_list = mother_list_gen(data_mac_addr)
            group = matrix_generate(bssid_list_len, bssid_list, data_mac_addr)
            #print data['pointArray']
            data_mac_addr_n.append(data['pointArray'])
            returnMat = matrix_generate(bssid_list_len, bssid_list, data_mac_addr_n)
            labels = return_label(data_address_addr)
            #print returnMat
            print classify0(returnMat[0], group, labels, 3) 
 

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
    app = web.application(urls,globals())
    app.run()
