#Evangelos Iliadis 3117

import csv

#Part 2
#Paths. Should be changed accordingly
path = '/usr/home/students/stud15/cse53117/Desktop/ddtry/tiger_roads.csv'
grdPath = '/usr/home/students/stud15/cse53117/Desktop/ddtry/grid.grd'
dirPath = '/usr/home/students/stud15/cse53117/Desktop/ddtry/grid.dir'
queriesPath = '/usr/home/students/stud15/cse53117/Desktop/ddtry/queries.txt'

originalRecords = []  #The data structure that was requested in part 1 of the assignment
records = [[list() for i in range(10)] for j in range(10)]  #Here will be stored the contents of the grid.grd file
queries = []  #The given queries

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
            originalRecords.append(singleRecord) #Add each record line in the records data structure
            counter += 1

#Extract data
def extract_from_grd():
    with open(dirPath, mode='r') as dir, open(grdPath, mode='r') as grd:  # Open files for reading.
        firstLine = dir.readline() #Skip first line
        for line in dir:
            splitLine = line.split() #Split dir line contents where there are spaces " "

            for i in range(int(splitLine[3])): #splitLine[3] represents the number of roads that belong to each cell
                #So we need to append the next int(splitLine[3]) elements, from grid.grd, into the correct list cell
                add_to_list(int(splitLine[1]),int(splitLine[2]),grd.readline()) #grd.readline() reads the next grd line

#Add the data from the grd file to the record data structure
def add_to_list(x,y,record):
    splitRecord = [ '"{}"'.format(x) for x in list(csv.reader([record], delimiter=',', quotechar='"'))[0] ]
    records[x][y].append(splitRecord)

#Read queries from the queries.txt file
def read_queries():
    queries = []
    with open(queriesPath, mode='r') as que: #Open file for reading
        for line in que:
            query = []
            line = line.split(',')[1] #extract each query coordinate by splitting each line with the ',' delimiter
            for x in line.split():
                query.append(float(x)) #Since it's string cast it as float so we can use it for numeric operations
            queries.append(query)
    return queries

#Dataset min/ max
#We need to find the min/ max of MBR's in order to create the grid.
def find_min_max():
    counter = 1
    for record in originalRecords:
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

#Detect the intersection between the MBR of an object and the query window
def detect_intersection():
    for query in queries: #For each query
        counter = xIndex = yIndex = 0 #Helper counters. Help with the position while traversing through the grid
        cellIntersections = 0 #Count number of cells intersected by the query window
        intersectedObjects = [] #List of the intersected objects
        windowCo = [query[0],query[2],query[1],query[3]] #Query window coordinates. They have been "swapped" around a bit so that the order is xmin,ymin,xmax,ymax
        for cell in grid:
            if(windowCo[0] <= cell[2] and windowCo[2] >= cell[0] and windowCo[1] <= cell[3] and windowCo[3] >= cell[1]): #Compare window/ cell bottom left and top right edges
                cellIntersections += 1 #If they intersect increase counter by 1
                for i in range(len(records[yIndex][xIndex])): #len(records[yIndex][xIndex]) is the number of objects that belong in each grid
                    objectCo = originalRecords[int(records[yIndex][xIndex][i][0].strip('\"'))-1][1]
                    #objectCo broken down step by step.
                    #1. records[yIndex][xIndex][i][0] is the ID of each one of the objects that belong in each cell.
                    #2. records[yIndex][xIndex][i][0].strip('\"') removes the excess ' "" ' that were added by the convertion from grd to data structure
                    #3. int(records[yIndex][xIndex][i][0].strip('\"'))-1. Since it's string make it int. -1 since the the object with ID = n, is in the n-1 position of the list
                    #4. originalRecords[int(records[yIndex][xIndex][i][0].strip('\"'))-1][1]. We use original records since the convertion form grd messes up with the record syntax.
                    if(windowCo[0] <= objectCo[1][0] and windowCo[1] <= objectCo[1][1]): #Compare object MBR and window bottom-left/top-right edge coordinates
                        if(windowCo[2] >= objectCo[0][0] and windowCo[3] >= objectCo[0][1]):
                            if(originalRecords[int(records[yIndex][xIndex][i][0].strip('\"'))-1][0] not in intersectedObjects): #Only add the ID if it's the first time detecting it.Filter out the duplicates
                                intersectedObjects.append(originalRecords[int(records[yIndex][xIndex][i][0].strip('\"'))-1][0])
            #Set our position in the grid
            counter += 1
            if counter % 10 == 0:
                yIndex = 0
                xIndex += 1
            else:
                yIndex += 1
        queryObjectResults.append(intersectedObjects)
        queryCellResults.append(cellIntersections)

#Print results in the requested format
def print_results():
    for i in range(len(queries)):
        print('Query',i+1 ,'results:')
        print(*queryObjectResults[i], sep = ' ')
        print('Cells:',queryCellResults[i])
        print('Results:',len(queryObjectResults[i]))
        print('----------')

#Main
extract_from_grd()
queries = read_queries()
queryObjectResults = []
queryCellResults = []
extract_data()
datasetMinMax = find_min_max()
cellDimensions = calculateCellDimensions()
xBoundaries = calculateBoundaries('x')
yBoundaries = calculateBoundaries('y')
grid = createGrid()
detect_intersection()
print_results()