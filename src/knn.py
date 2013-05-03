from numpy import *
import operator

def createDataSet():
	group = array([[1.0, 1.1], [1.0, 1.0], [0, 0], [0, 0.1]])
	labels = ['A', 'A', 'B', 'B']
	return group,labels


def classify0(inX, dataSet, labels, k):
	dataSetSize = dataSet.shape[0]
	diffMat = tile(inX, (dataSetSize, 1)) - dataSet
	sqDiffMat = diffMat ** 2
	sqDistances = sqDiffMat.sum(axis = 1)
	distances = sqDistances ** 0.5
	#print distances
	sortedDistIndicies = distances.argsort()
	#print sortedDistIndicies
	classCount={}
	for i in range(k):
		voteIlabel = labels[sortedDistIndicies[i]]
		#print voteIlabel
		classCount[voteIlabel] = classCount.get(voteIlabel,0) + 1
		#print classCount
	sortedClassCount = sorted(classCount.iteritems(), 
		key=operator.itemgetter(1), reverse = True)
	#print sortedClassCount
	return sortedClassCount[0][0]



group,labels = createDataSet()
#print group,labels

print classify0([0,0], group, labels, 3)