import json
from numpy import *
FILE = 'davis3rd3.json'
#FILE = 'homewifi2.json'
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
    return list(frozenset(total_BSSID)), len(list(frozenset(total_BSSID)))


def mother_list_gen(mac_addr_list):
    bssid_list, bssid_list_len = generate_bssid_label(mac_addr_list)
    return bssid_list_len, bssid_list


# generate BSSID signal strength matrix for each AP
def matrix_generate(bssid_list_len, bssid_list, mac_addr_list_new):
    #print returnMat
    #print bssid_list, bssid_list_len
    mac_addr_list_len = len(mac_addr_list_new)
    returnMat = zeros((mac_addr_list_len, bssid_list_len))
    i = 0
    for mac_addr in mac_addr_list_new:
        i = i + 1
        for data in mac_addr:
            #print data['BSSID'], data['level']
            try:
                bssid_index = bssid_list.index(data['BSSID'])
                returnMat[i - 1, bssid_index] = data['level']
            except Exception:
                pass
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


def parse_new_json(i):
    #i = 12
    f = open('./test2.json')
    #f = open('./homewifi.json')
    data = json.load(f)
    data_mac_addr_n = []
    data_address_addr_n = []
    data_mac_addr, data_address_addr = init_mac_addr()
    for item in data['wifiInfos']:
            #print item['point']
            #print len(item['pointArray'])
            data_mac_addr_n.append(item['pointArray'])
            data_address_addr_n.append(item['point'])
    returnMat, bssid_list = mother_list_gen(data_mac_addr)
    returnMat = matrix_generate(returnMat, bssid_list, data_mac_addr_n)
    #print returnMat[i], data_address_addr_n[i]
    return returnMat[i]

parse_new_json(85)
#matrix_and_label(data_mac_addr)

#li, len = generate_bssid_label(data_mac_addr)

#return_label(init_mac_addr())
