import json
from numpy import *
FILE = 'homewifi.json'
f = open(FILE)
data = json.load(f)



	


# return the maxium number of Wifi AP Mac addr in this environment
def find_max_length(mac_addr_list):
	addr_length_list = []
	for item in mac_addr_list:
		addr_length_list.append(len(item))
	#print addr_length_list
	return max(addr_length_list)

# return label list and list size
def generate_bssid_label(mac_addr_list):
	total_BSSID = []
	for mac_addr in mac_addr_list:
		for BSSID in mac_addr:
			total_BSSID.append(BSSID['BSSID'])
			#print BSSID['BSSID']
	return list(frozenset(total_BSSID)), len(list(frozenset(total_BSSID)))



# generate BSSID signal strength matrix for each AP
def matrix_generate(mac_addr_list):
	bssid_list, bssid_list_len = generate_bssid_label(mac_addr_list)
	#print bssid_list, bssid_list_len
	mac_addr_list_len = len(mac_addr_list)
	returnMat = zeros((mac_addr_list_len, bssid_list_len))
	#print returnMat
	i = 0
	for mac_addr in mac_addr_list:
		i = i + 1
		for data in mac_addr:
			#print data['BSSID'], data['level']
			bssid_index = bssid_list.index(data['BSSID'])
			returnMat[i - 1, bssid_index] =  data['level']
	return returnMat

# parse json and initial data
def init_mac_addr():
	data_mac_addr = []
	data_address_addr = []
	for item in data['wifiInfos']:
		#print item['point']
		#print len(item['pointArray'])
		data_mac_addr.append(item['pointArray'])
		data_address_addr.append(item['point'])
	return data_mac_addr, data_address_addr

#find_max_length(data_mac_addr)
#x, y = init_mac_addr()

# return label for kNN
def return_label(data_address_addr):
	label = []
	for item in data_address_addr:
		label.append(str(item['x'])+","+str(item['y']))
	return label


def parse_new_json():
	i = 68
	f = open('./test.json')
	data = json.load(f)
	data_mac_addr = []
	data_address_addr = []
	for item in data['wifiInfos']:
			#print item['point']
			#print len(item['pointArray'])
			data_mac_addr.append(item['pointArray'])
			data_address_addr.append(item['point'])
	print matrix_generate(data_mac_addr)[i], data_address_addr[i]
	return matrix_generate(data_mac_addr)[i]


#matrix_and_label(data_mac_addr)

#li, len = generate_bssid_label(data_mac_addr)

#return_label(init_mac_addr())