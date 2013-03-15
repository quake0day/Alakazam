---
layout: default
title: Documentation
---

# Documentation

## Project Plan

### Time table & Schedule

Date         | Mission
------------ | ------------
Before March | Set up github page (blog), set up basic development environment (Eclipse, Linux server)
Mar.1 - Mar.15 | Develop android app to collect smartphone sensor data (Wifi, gps, compass,gyroscope). For server side, provide API and pro- tocol to save and transmit those data
Mar.16 - Mar.31 | Let the android app provide more accurate sensor data (filter out noise).UI design and develop. For server side, process those sensor data and clustering it (on sensor features and locations)
Apr.1 - Apr.30 | Using clustering information and decision tree to detecting seed landmarks and form a new organic landmarks. Send back infor- mation to user side and display. Do experiment on how accurate (meter) this system can provide. Do experiment to see whether this system can self-evolved and achieved a better result after running few weeks.


###Milestones
1.  Programming Android phones to obtain all the sensor data (without consider the energy cost) and send to a server with timestamp.
2.  Server abstract the sensor features and cluster it.
3. Server map those cluster on sensor features to locations of cluster members.
4. Server form a new organic landmarks (you can think it as an automatically generated location anchor) by using those info.
5. Server using decision tree for detecting seed landmarks (you can think it as an user tagged anchor).


## Demo

In the end of this semester we will perform our demo by showing an app running on an android phone which can show whose office or lab the user is approaching (Dr.Demirbas - Room 313, Dr.Regan - Room 326, Dr. Selman - Room 349, PhoneLab etc..). We assume we have the floor plan in advance, but the location fingerprint is formed by our algorithm not manually added.
