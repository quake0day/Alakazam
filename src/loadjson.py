import json

f = open('./homewifi.json')
data = json.load(f)

for item in data['wifiInfos']:
	#print item['point']
	print item['pointArray'][1]['BSSID']

