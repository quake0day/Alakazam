---
layout: default
title: A solution of indoor localization
---

## What's New


{% for post in site.posts %}
---
<a href="/Alakazam{{ post.url }}">
<h3>{{ post.title }}</h3>
<span class="date">
<div class="dateday">{{ post.date | date: "%e" }}</div>
<div>{{ post.date | date: "%b" }}</div>
<div class="dateyear">{{ post.date | date: "%Y" }}</div>
</span>
</a>

{{ post.content }}
{% endfor %}

---
##About Alakazam
Our project aims at providing a solution of **indoor localization**. We’ve already searched several papers related to indoor localization by using smart phone ([1],[2],[3]). 

[1] is a good starting point and we aim our goal to have accuracy within 2 meters, utilizing all kinds of built-in sen- sors in current mobile devices, such as GPS, gyroscope, compass, wireless module, etc. In the online-version proposal, you mention that “as part of the PhoneLab project, we have made significant improvements over Android baseline proximity service to make it energy-efficient, easier to use, and precise.” 

The difference between existing approach and our approach is that we want to use the method described in [2] paper to form a unsupervised indoor localiza- tion scheme by pass the need for human interaction (without using the localization tag but localization tag may help us to recognize the place).


###References
[1] Azizyan, Martin and Constandache, Ionut and Roy Choudhury, Romit, ”SurroundSense: mobile phone localization via ambience fingerprinting,” MobiCom ’09, 2009.  
[2] Wang, He and Sen, Souvik and Elgohary, Ahmed and Farid, Moustafa and Youssef, Moustafa and Choudhury, Romit Roy, ”No need to war-drive: unsupervised indoor lo- calization,” MobiSys ’12, 2012.  
[3] Yang, Zheng and Wu, Chenshu and Liu, Yunhao, ”Locating in fingerprint space: wireless indoor localization with little human intervention,” MobiCom’12, 2012.  




-----
If you find any bugs, please help us by reporting them in our
[bug tracker](https://github.com/quake0day/Alakazam/issues/new).
If you encounter any problems using Alkazam, please [contact us](<schen23@buffalo.edu>).
