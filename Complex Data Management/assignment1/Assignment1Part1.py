#Evangelos Iliadis 3117

import csv
import numpy as np
#from matplotlib import pyplot as plt

#part 1

#Path. Should be changed accordingly for each file.
path = '/usr/home/students/stud15/cse53117/Desktop/ddtry/acs2015_census_tract_data.csv'
#print(path)

incomes = [] #Here will be stored all the valid income elements.
columnCounter = 0 #Index to the number of the 'Income' column.

#Data extraction

with open(path, mode = 'r') as inf: #Open csv file for reading.
    reader = csv.reader(inf, delimiter = ',') #Determine the delimiter that seperates each cell. 
    firstRow = next(reader) #Get first row of the opened csv file.
    
    
    #Find the column that contains the 'Income data.
    for col in firstRow:
        if col == 'Income':
            break #Found 'Income' column. Break.
        else:
            columnCounter += 1 #For each column that isn't 'Income', columnCounter++.
    #print(columnCounter)
    
    #Add income data to array
    for row in reader:
        if row[columnCounter].strip(): #We need only the valid entries.
            #print(row[columnCounter].strip())
            #We use float() function because we need to find min/max later on. 
            #So we need float values in order to compare them.
            incomes.append(float(row[columnCounter])) 
    print(len(incomes), 'valid income values')

minIncome = min(incomes) #Find min income value.
maxIncome = max(incomes) #Find max income value.

print ('minimum income =', minIncome, 'maximum income =', maxIncome)

#Equi-Width

binWidth = (maxIncome - minIncome)/100 #Each bin needs to have the same width. So we divide the min/max spectrum into 100 equal bins.
binEdges = [] #Here will be stored the histogram edges for each bin.
for i in range(101): #101 cause with 100 we don't cover the full income value spectrum.
    roundedBinEdge = minIncome + i*binWidth #Find i'th bin edge.
    binEdges.append(roundedBinEdge) #Add the i'th bin edge to the list.
#print(binEdges)

binRanges = [] #Each element is a histogram bin range.
binRange = [0,0] #Dummy for bin range. Will be replaced later on with the real edges.
for i in range(100):
    binRange = [binEdges[i],binEdges[i+1]]
    binRanges.append(binRange)
#for i in range(100):
#    print(binRanges[i])

numtuples = [0]*100 #Create an array full of one hundred 0's.

#Find the number of tuples in each histogram bin.
for i in incomes: #For each income element.
    numtuplesIndex = 0 #Will help us determine which numtuples element should be increased by 1.
    counter = 0 #Helper counter. Keeps track of the bin we're in.
    for j in binRanges: #For each bin.
        if counter < 99:
            if i >= j[0] and i< j[1]: #If income value is between the two bin edges.
                numtuples[numtuplesIndex] += 1 #Increase it by 1.
            else:
                numtuplesIndex += 1 #Else keep searching. Increase helper index by 1.
        else:
            if i >= j[0] and i<= j[1]: #If income value is between the two bin edges.
                numtuples[numtuplesIndex] += 1 #Increase it by 1.
            else:
                numtuplesIndex += 1 #Else keep searching. Increase helper index by 1.
        counter += 1

#equiwidth print
print('equiwidth:')
for i in range(100):
        print('range:', '[' , round(binEdges[i],2), ',', round(binEdges[i+1],2), ')' , ',' , 'numtuples:', numtuples[i])

#equidepth
sortedIncomes = np.sort(incomes) #Sort incomes in an ascending order, then save them in a new array.
actualBinSize = len(sortedIncomes)/100 #Float division.
binSize = int(len(sortedIncomes)/100) #Int value of bin size.
#print(binSize)

groupedIncomes = [] #Group the sorted incomes so they can be distributed in their respective bin.
incomesIndex = 0 #Index to the sortedIncomes array.
for i in range(100):
    incomeGroup = [] #Helper array.
    for j in range(binSize):
        incomeGroup.append(sortedIncomes[incomesIndex])
        incomesIndex += 1
    groupedIncomes.append(incomeGroup)

#for i in range(100):
#    print(i, len(groupedIncomes[i]))

groupEdges = []
groupEdges.append(groupedIncomes[0][0])
for i in range(100):
    groupEdges.append(groupedIncomes[i][len(groupedIncomes[i])-1])

#for i in range(101):
#    print(groupEdges[i])

groupRanges = [] #Each element is a histogram bin range.
groupRange = [0,0] #Dummy for bin range. Will be replaced later on with the real edges.
for i in range(100):
    groupRange = [groupEdges[i],groupEdges[i+1]]
    groupRanges.append(groupRange)

#equidepth print
print('equidepth:')
for i in range(100):
    if i < 99:
        print('range:', '[' ,groupedIncomes[i][0], ',', groupedIncomes[i+1][0],')', ',' , 'numtuples:', len(groupedIncomes[i]))
    else:
        print('range:', '[' ,groupedIncomes[i][0], ',', groupedIncomes[i][len(groupedIncomes[i])-1],']', ',' , 'numtuples:', len(groupedIncomes[i]))

# equiwidth plot creation
#plt.hist(incomes, binEdges, alpha = 0.5, edgecolor = 'black', label = 'equiwidth')

# equidepth plot creation
#plt.hist(sortedIncomes, groupEdges, alpha = 0.5, edgecolor = 'black', label = 'equidepth')

# diagram creation
#plt.xlabel('value')
#plt.ylabel('frequency')
#plt.legend(loc='upper right')
#plt.show()