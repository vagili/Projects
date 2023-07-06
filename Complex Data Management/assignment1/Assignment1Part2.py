#Evangelos Iliadis 3117

import csv
import numpy as np

#part 2
path = '/usr/home/students/stud15/cse53117/Desktop/ddtry/acs2015_census_tract_data.csv'

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
    #print(len(incomes), 'valid income values')

minIncome = min(incomes) #Find min income value.
maxIncome = max(incomes) #Find max income value.

#print ('minimum income =', minIncome, 'maximum income =', maxIncome)

#Equi-Width

binWidth = (maxIncome - minIncome)/100 #Each bin needs to have the same width. So we divide the min/max spectrum into 100 equal bins.
binEdges = [] #Here will be stored the histogram edges for each bin.
for i in range(101): #101 cause with 100 we don't cover the full income value spectrum.
    roundedBinEdge = minIncome + i*binWidth #Find i'th bin edge. Round it to 2 decimals.
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

#equidepth

sortedIncomes = np.sort(incomes) #Sort incomes in an ascending order, then save them in a new array.
actualBinSize = len(sortedIncomes)/100 #Float division.
binSize = int(len(sortedIncomes)/100) #Int value of bin size.
#print(len(sortedIncomes))

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

#input
while(1):
    a = input('Give a positive integer for a:')
    try:       
        a = int(a) #save input as int
        if(a < 0): #input should be a positive integer
            print('Input is not positive! Try again:')
            continue
        break
    except ValueError:
        print('Invalid input! Try again:')        
while(1):
    b = input('Give a positive integer for b:')
    try:       
        b = int(b) #save input as int
        if(b < 0): #input should be a positive integer
            print('Input is not positive! Try again:')
            continue
        break
    except ValueError:
        print('Invalid input! Try again:')
        
if(a > b):
    a, b = b, a #if a>b, swap a with b.

actualResults = 0
for i in incomes:
    if i >= a and i < b:
        actualResults += 1

inputRange = b - a
#print('inputrange:', inputRange)

equiWEstRes = 0.0
tempa = a
tempb = b
#print('tempa:', tempa, 'tempb:', tempb)

#If a is lower than the first edge, but b is higher.
if tempa < binEdges[0] and tempb >= binEdges[0] and actualResults != 0:
    tempa = binEdges[0]

for i in range(100): #for every bin
    if (tempa >= binEdges[i] and tempa < binEdges[i+1]) and b < binEdges[i+1]: #check if the given range [a,b] belongs within a single bin.
        percentage = (tempb - tempa)/ (binEdges[i+1] - binEdges[i]) #calculate the percentage of the bin that is the same as the given range.
        equiWEstRes += percentage*numtuples[i] #add the new results to the existing ones.
        #print(percentage*numtuples[i])
        break #this will be the last calculation. Exit loop.
    elif (tempa >= binEdges[i] and tempa < binEdges[i+1]) and b >= binEdges[i+1]: #if the given a is within the current bin range, but b surpasses it.
        percentage = (binEdges[i+1] - tempa)/ (binEdges[i+1] - binEdges[i]) #calculate the percentage of the bin that is the same as the given range.
        equiWEstRes += percentage*numtuples[i]  #add the new results to the existing ones.
        tempa = binEdges[i+1] #set a as the current bin's second edge.
        #print(percentage*numtuples[i])

inputRange = b - a
equiDEstRes = 0.0
tempa = a
tempb = b

#If a is lower than the first edge, but b is higher.
if tempa < groupedIncomes[0][0] and tempb >= groupedIncomes[0][0] and actualResults != 0:
    tempa = groupedIncomes[0][0]

for i in range(100):
    if i == 99:
        secondEdge = groupedIncomes[i][len(groupedIncomes[i])-1] #If it's the last bin, secondEdge(b) is equal to the last element of it.
    else:
        secondEdge = groupedIncomes[i+1][0] #If it's the last bin, secondEdge(b) is equal to the first element of the next bin.

    if ((tempa >= groupedIncomes[i][0] and tempa < secondEdge) and tempb < secondEdge): #check if the given range [a,b] belongs within a single bin.
        percentage = (tempb - tempa)/ (secondEdge - groupedIncomes[i][0]) #calculate the percentage of the bin that is the same as the given range.
        equiDEstRes += percentage*len(groupedIncomes[i]) #add the new results to the existing ones.
        break #Last calculation is done. Exit loop.
    elif (tempa >= groupedIncomes[i][0] and tempa < secondEdge) and b >= secondEdge: #if the given a is within the current bin range, but b surpasses it.
        percentage = (secondEdge - tempa)/ (secondEdge - groupedIncomes[i][0]) #calculate the percentage of the bin that is the same as the given range.
        equiDEstRes += percentage*len(groupedIncomes[i]) #add the new results to the existing ones.
        tempa = secondEdge #set a as the current bin's second edge.

print('equiwidth estimated results:', equiWEstRes)
print('equidepth estimated results:', equiDEstRes)
print('actual results:', actualResults)