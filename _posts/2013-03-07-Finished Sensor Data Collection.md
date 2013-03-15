---
layout: post
title: Sensor Data Collection Almost Done!
---

As we promised in our **proposal**: before Mar.15, we need to develop android app to collect smartphone sensor data (Wifi, gps, compass,gyroscope) and for server side, we need to provide API and protocol to save and transmit those data. 


And now, we glad to say that we successfully finished our first mission! We created an Android app. Use this Android app we can collect sensor data in real time. Then, this app will send sensor data to our sever by using HTTP post. 

The server side are running webpy, which is a simple www service, webpy will get the post data and save those data into database for further use.

The next step is letting the android app provide more accurate sensor data and for server side, process those sensor data and clustering it (base on sensor features and locations)

Cheers!

CS