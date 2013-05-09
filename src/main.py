#!/usr/bin/python
# -*-coding:utf-8 -*-
import sys;
import os;
import time;
import threading;
import web
import query
from knn import *
import json
from loadjson import *
from insert_data import *
from caldis import *
urls = (
    '/', 'index',
    '/loc', 'loc',
    '/wifi', 'wifi',
    '/sensor', 'sensor',
    '/submit', 'submit'
)

lat = 0
lng = 0


CONVERT_MATRIX = [[0,0], [43.002486,-78.787595], [43.002684,-78.7877540],
[43.0028552758727,-78.78761932253838],
[43.002957276509164,-78.7876608967781],
[43.00261694680236,-78.78770783543587],
[43.00242863675962,-78.78764480352402],
[43.0027493519556,-78.78728806972504],
[43.00279741005173,-78.78686159849167],
[43.00285625664887,-78.78707751631737]]


CONVERT_MATRIX2 = [[0,0],[43.002486,-78.787595],
[43.002684,-78.7877540],
[43.01596685375833,-78.8475090265274],
[43.002957276509164,-78.7876608967781],
[43.00261694680236,-78.78770783543587],
[43.00242863675962,-78.78764480352402],
[43.01596685375833,-78.8475090265274]]

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
            print classify0(returnMat[0], group, labels, 6)
            cluster_val = classify0(returnMat[0], group, labels, 6)
            y = cluster_val.split(',')[1]
            real_loc = CONVERT_MATRIX[int(y)]
            global lat
            global lng
            lat = real_loc[0]
            lng = real_loc[1]
            activity = "standing"
            flag = 0
            insert_data(lat, lng, activity, flag)
            
        return lat,lng


class loc:
    def GET(self):
        db = query.init_Mongo()
        return query.query_one_loc(db)

    def POST(self):
        data = web.data()

        print data
        return "This post:)"


class sensor:
    def POST(self):
        i = web.input()
        print ">>>>>>>>>>>>>>>>>>>>>>",
        print i.bearing
        print i.steplength
        print i.meana
        print i.lat,i.lng
        #new_lat, new_lng = calDis(lat,lng,i.bearing,i.steplength)
        new_lat = i.lat
        new_lng = i.lng
        print new_lat,new_lng
        if(float(i.meana) < 12):
            activity = "walking"
        elif(float(i.meana) >= 12):
            activity = "running"
        flag = 1
        insert_data(new_lat, new_lng, activity, flag)
        return "Done"


class submit:

    def POST(self):
        return "This post"



class MTimerClass(threading.Thread):  # cookie监控时钟
    def __init__(self,fn,args=(),sleep=1):        
        threading.Thread.__init__(self)
        self.fn = fn  
        self.args = args  
        self.sleep = sleep 
        self.setDaemon(True)
        
        self.isPlay = True  #当前是否运行 
        self.fnPlay = False #当前已经完成运行
        self.thread_stop=False; 
        
    def SetSleep(self,sleep): # 重新设置 时间间隔
        self.sleep=sleep;
        
    def __do(self):
        self.fnPlay = True;
        apply(self.fn,self.args);
        self.fnPlay = False 

    def run(self): 
        while self.isPlay : 
            if self.thread_stop==True:
                break;
            #if SubCommon.ifexeStop==True:  #可以外部调用 来关掉线程。
            #    print 'thread break'
            #    break;  
            #print self.sleep;
            time.sleep(self.sleep) 
            self.__do();
            
    def stop(self):  
        #stop the loop  
        self.thread_stop = True;
        self.isPlay = False; 
        while True:
            if not self.fnPlay : break             
            time.sleep(0.01)

def GetSearchinfo():
    update_data("standing")
    # to do
    pass;
    


if __name__ == "__main__":
    app = web.application(urls, globals())
    tCheck=MTimerClass(GetSearchinfo, '',  10);
    tCheck.setDaemon(True); # 随主线程一起结果
    tCheck.start();         #线程启动
    app.run()
