#Evangelos Iliadis 3117

import csv
import os

#Part 1
#Paths. Should be changed accordingly. For grd and dir paths: These are the paths of the files that will be created when the code runs.
path = '/usr/home/students/stud15/cse53117/Desktop/ddtry/tiger_roads.csv'
grdPath = '/usr/home/students/stud15/cse53117/Desktop/ddtry/grid.grd'
dirPath = '/usr/home/students/stud15/cse53117/Desktop/ddtry/grid.dir'

records = [] #Here will be stored the csv data in the expected format
MBRIDList = [[list() for i in range(10)] for j in range(10)] #Here will be stored the road ID's that belong in each cell
sortedMBRIDList = [] #MBRIDList after the sorting

#Data extraction
#Extract the elements from the road csv file
def extract_data():
    with open(path, mode = 'r') as inf: #Open csv file for reading.
        reader = csv.reader(inf, delimiter = ',') #Determine the delimiter that seperates each cell.
        firstRow = next(reader) #Get first row of the opened csv file

        counter = 1 #Line ID
        for row in reader:
            singleRecord = [] #Each line that will be stored into records
            singleRecord.append(counter) #First cell should be the object ID
            rowCellStorage = [] #Cells will be stored here
            cellCounter = 0 #Index of columns
            for cell in row:
                splitCell = cell.split() #Extract the two "string" numbers from each cell
                floatSplitCell = [float(splitCell[0]),float(splitCell[1])] #Make them float and store them
                #Find MBRmin/ MBRmax
                if cellCounter == 0: #Assign first cell coordinates as MBR min/ max for the comparison. This will probably change with each iteration.
                    MBRminX = float(splitCell[0])
                    MBRminY = float(splitCell[1])
                    MBRmaxX = float(splitCell[0])
                    MBRmaxY = float(splitCell[1])
                else: #If current cell coordinates are higher/ lower than the current MBR min/ max replace them.
                    if float(splitCell[0]) < MBRminX:
                        MBRminX = float(splitCell[0])
                    if float(splitCell[0]) > MBRmaxX:
                        MBRmaxX = float(splitCell[0])
                    if float(splitCell[1]) < MBRminY:
                        MBRminY = float(splitCell[1])
                    if float(splitCell[1]) > MBRmaxY:
                        MBRmaxY = float(splitCell[1])
                rowCellStorage.append(floatSplitCell)
                cellCounter += 1
            #Create the MBR matrix. It will be consisted of two sub matrices, the MBRMin/ MBRMax.
            MBRmin = [MBRminX,MBRminY]
            MBRmax = [MBRmaxX,MBRmaxY]
            MBR = []
            MBR.append(MBRmin)
            MBR.append(MBRmax)
            singleRecord.append(MBR) #Put MBR's array as the 2nd element
            singleRecord.append(rowCellStorage) #Put the cells array as the 3rd element
            records.append(singleRecord) #Add each record line in the records data structure
            counter += 1

#Dataset min/ max
#We need to find the min/ max of MBR's in order to create the grid.
def find_min_max():
    counter = 1
    for record in records:
        if counter == 1: #1st iteration. Set min/ max as the 1st element's min/ max.
            datasetMinX = record[1][0][0]
            datasetMinY = record[1][0][1]
            datasetMaxX = record[1][1][0]
            datasetMaxY = record[1][1][1]
        else:
            if record[1][0][0] < datasetMinX:
                datasetMinX = record[1][0][0]
            if record[1][0][1] < datasetMinY:
                datasetMinY = record[1][0][1]
            if record[1][1][0] > datasetMaxX:
                datasetMaxX = record[1][1][0]
            if record[1][1][1] > datasetMaxY:
                datasetMaxY = record[1][1][1]
        counter += 1
    return datasetMinX, datasetMinY, datasetMaxX, datasetMaxY

#Calculate cell width/ height
def calculateCellDimensions():
    cellWidth = (datasetMinMax[2] - datasetMinMax[0])/10
    cellHeight = (datasetMinMax[3] - datasetMinMax[1])/10

    return cellWidth,cellHeight

#Calculate the x/ y coordinates of each cell edge using width/ height
def calculateBoundaries(axis):
    boundaries = []
    if axis == 'x':
        for i in range(11):
            boundaries.append(datasetMinMax[0] + i*cellDimensions[0])
    elif axis == 'y':
        for i in range(11):
            boundaries.append(datasetMinMax[1] + i*cellDimensions[1])
    else:
        print('Axis does not exist')
    return boundaries

#Create grid
def createGrid():
    grid = []
    y = 0
    for i in reversed(range(10)):
        x = 0
        for j in range(10):
            #Each cell will contain the bottom left and top right corner coordinates
            cell = [xBoundaries[x],yBoundaries[y],xBoundaries[x+1],yBoundaries[y+1]]
            grid.append(cell)
            x += 1
        y += 1
    return grid

def findCells(): #Now we need to find where each record belongs
    for record in records: #For each record
        counter = xIndex = yIndex = 0 #Counter increased with each cell. Helpful to set xIndex/ yIndex which are our position in the grid.
        xMinIndex = yMinIndex = xMaxIndex = yMaxIndex = -1 #min/ max indices represent the first/ last cell that each record route occupies.
        minFlag = maxFlag = 1

        for cell in grid: #For each grid cell
            #Compare the upper lower edges of each cell with the record MBR's to see if it belongs in it.
            if (record[1][0][0] >= cell[0] and record[1][0][0] <= cell[2]) and (record[1][0][1] >= cell[1] and record[1][0][1] <= cell[3]):
                if minFlag == 1: #Do this only once.
                    xMinIndex = xIndex
                    yMinIndex = yIndex
                    minFlag = 0
            if (record[1][1][0] >= cell[0] and record[1][1][0] <= cell[2]) and (record[1][1][1] >= cell[1] and record[1][1][1] <= cell[3]):
                if maxFlag == 1: #Do this only once.
                    xMaxIndex = xIndex
                    yMaxIndex = yIndex
                    maxFlag = 0

            #Set x/y indices in the grid
            counter += 1
            if counter % 10 == 0:
                xIndex = 0
                yIndex += 1
            else:
                xIndex += 1
        addToIDList(record[0], xMinIndex, yMinIndex, xMaxIndex, yMaxIndex)

#For each cell between the min/max cells add the record ID
def addToIDList(id,xMin,yMin,xMax,yMax):
    for y in range(10):
        for x in range(10):
            if (x>=xMin and x<=xMax) and (y>=yMin and y<=yMax):
                MBRIDList[y][x].append(id)

#Sort the MBRList in the specific order that is request
def sortMBRIDList():
    listStorage = []
    for y in reversed(range(10)):
        for x in range(10):
            listStorage.append(MBRIDList[x][y])

#Create grd file
def createGrdFile():
    if(os.path.isfile(grdPath)): #If it already exists delete it. Cause it may need an update
        os.remove("grid.grd")
    f = open("grid.grd", "w") #Create file for writing
    for y in range(10):
        for x in range(10):
            for id in MBRIDList[x][y]:
                record = records[id-1]
                strRecord = convertTupleToString(record) + "\n"
                f.write(strRecord)

#Convert tuple to string so it can be added to the file
def convertTupleToString(tuple):
    strTuple = ""
    for item in tuple:
        if isinstance(item, int):
            strTuple += str(item)+','
        else:
            strTuple += str(item)
    return strTuple

#Create dir file
def createDirFile():
    if(os.path.isfile(dirPath)): #If it already exists delete it. Cause it may need an update
        os.remove("grid.dir")
    f = open("grid.dir", "w") #Create file for writing
    f.write("%d %f %f %f %f\n" %(1,datasetMinMax[0],datasetMinMax[2],datasetMinMax[1],datasetMinMax[3]))
    linenumber = 2
    for y in range(10):
        for x in range(10):
            f.write("%d %d %d %d\n" %(linenumber,y,x,len(MBRIDList[x][y])))
            linenumber += 1

#Main
extract_data()
datasetMinMax = find_min_max()
cellDimensions = calculateCellDimensions()
xBoundaries = calculateBoundaries('x')
yBoundaries = calculateBoundaries('y')
grid = createGrid()
findCells()
createGrdFile()
createDirFile()