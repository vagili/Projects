#Evangelos Iliadis 3117

import sys
import heapq

#Paths
rndPath = '/usr/home/students/stud15/cse53117/Desktop/ddtry/rnd.txt'
seq1Path = '/usr/home/students/stud15/cse53117/Desktop/ddtry/seq1.txt'
seq2Path = '/usr/home/students/stud15/cse53117/Desktop/ddtry/seq2.txt'

R = [] #Here will be stored the contents of rnd.txt
seen = {} #Dictionary. Holds all the objects that we've seen so far
sequential_accesses = 0 #Number of sequential accesses
heap_IDs = set() #The k ID's of the objects that are currently in the heap

#Add contents of rnd to R array.
def add_from_rnd():
    with open(rndPath, mode='r') as rnd: #Open rnd.txt for reading
        for line in rnd: #For each line
            splitLine = line.split() #Split line to get it's contents. id,rating
            R.append(float(splitLine[1])) #Add to array

#Create min heap
def declare_min_heap():
    global Wk #Since Wk is created inside a function, "global" is needed
    Wk = sorted(seen.values())[:] #Put the elements that we've seen, sorted, inside the min heap
    heap_IDs = {tuple[0] for tuple in Wk} #Keep the heap ID's in a set.

#Sort heap tuples
def sort_heap():
    global Wk
    #In our case heap consists of 3-element tuples(objects)
    #So heapq.heapify() may not work properly.
    Wk = sorted(list(Wk), key=lambda tuple: tuple[1])[:] #Sort Wk, comparing the field of "summary"
    heap_IDs = list(Wk)[:][0] # : is a slicer. It helps iterate through, keeping the ID fields in the process

# Exit check
def check_exit(flag,last1,last2,exit):
    threshold = last1 + last2 + 5.0  # Calculate threshold as instructed
    # We should bother with this only if the heap has been created. In other words only after..
    # ..we've encountered k objects. If threshold <= Wk[k-1][1] we've passed the first exit condition
    if flag == 1 and threshold <= Wk[k - 1][1]:
        # We should exit only if there are no potential objects that we've seen with values greater than..
        # ..the top heap object. In other words, in order to exit, we need the potential upper bound of all the objects..
        # ..that we've seen, that are not in the heap, to be less than the value of the top heap object.
        # For the objects that we've read in seq1, the max possible value is last2 and for the ones from seq2, it is..
        # .. last1. Since those files are sorted, the rest of the unread lines could only be as high as the last one we've read.
        # So even if the rest of the seen elements would get the max possible value, it would still not be enough to get into the heap.
        if any(key not in heap_IDs and (seen[key][2] == "line1" and seen[key][1] + last2 > Wk[0][1]) or \
               (seen[key][2] == "line2" and (seen[key][1] + last1 > Wk[0][1])) for key in seen):
            exit = 0
        return exit

def round_robin():
    with open(seq1Path, mode = 'r') as seq1, open(seq2Path, mode = 'r') as seq2: #Open seq1,seq2 for reading
        global sequential_accesses
        last1 = last2 = 0.0 #Here will be stored the last value of the lines we've just read.
        flag = 0 #Binary flag. Will be set to 1 once the heap has been created.
        while True: #Loop until break
            exit = 1 #Binary value. Once we do the condition check, if exit == 1 break.
            # Read next line from seq1. And split it.
            line1 = seq1.readline().split() #line1 = [ID,value]
            if line1: #If next line is not null
                sequential_accesses += 1 #Increase sequential accesses by 1
                last1 = float(line1[1]) #Cast the value field to float
                if line1[0] in seen: #If we've already read this ID from the other source file
                    #Add the previous lower bound with the new line source value
                    #Round it to 2 decimals
                    summary = round(sum([float(seen[line1[0]][1]), float(line1[1])]), 2)
                    #Store in dictionary. seen[id] = (id,value,string)
                    #String can be "line1","line2" or "both" depending on where we've seen..
                    #..it so far. Since this is the second time we encounter the same id we put "both"
                    seen[line1[0]] = ((line1[0]), summary, "both")
                    #If heap has been created and summary > value of the top heap element..
                    #..and the object isn't already in the heap
                    if flag == 1 and summary > Wk[0][1] and line1[0] not in heap_IDs:
                        heap_IDs.discard(Wk[0][0]) #Discard first object id from set
                        Wk.pop(0) #Pop first object from heap
                        heapq.heappush(Wk, seen[line1[0]])#Push the new object in heap whose sum was greater
                        sort_heap()#Sort the heap
                        heap_IDs.add(line1[0])#Add the id of the newly added object to the ID's set
                    #If heap has been created and summary > value of the top heap element..
                    #..but the object is already in the heap
                    elif flag == 1 and summary > Wk[0][1] and line1[0] in heap_IDs:
                        #Search for the index that shows its position in the heap
                        index = next((i for i, tuple in enumerate(Wk) if tuple[0] == line1[0]), -1)
                        #Once found, remove the outdated object.Add the updated version and sort the heap
                        if index != -1:
                            Wk.pop(index)
                            heapq.heappush(Wk, seen[line1[0]])
                            sort_heap()
                else: #If this is the first time we encounter this object ID
                    #Get sum of line value + rnd source value.Round it to 2 decimals
                    summary = round(sum([float(line1[1]), float(R[int(line1[0])])]), 2)
                    #Since this is the first time we encounter this object in seq1, we put "line1"
                    seen[line1[0]] = ((line1[0], summary, "line1"))
                    #If heap hasn't been created yet and we've reached k objects read, create the heap.
                    if flag == 0 and len(seen) == k:
                        declare_min_heap()
                        sort_heap()
                        flag = 1 #Set flag to 1 since the heap has been created
                    elif flag == 1 and summary > Wk[0][1]: #If heap already exists and sum> top element
                        heap_IDs.discard(Wk[0][0]) #Remove top object id from the id's set
                        Wk.pop(0) #Remove top object from heap
                        heapq.heappush(Wk, seen[line1[0]]) #Add new object
                        sort_heap() #Sort heap
                        heap_IDs.add(line1[0]) #Add new object ID to the id's set

                if check_exit(flag,last1,last2,exit) == 1:
                    break

            exit = 1#Binary value. Once we do the condition check, if exit == 1 break.
            line2 = seq2.readline().split()#line2 = [ID,value]
            if line2: #If next line is not null
                sequential_accesses += 1 #Increase sequential accesses by 1
                last2 = float(line2[1]) #Cast the value field to float
                if line2[0] in seen: #If we've already read this ID from the other source file
                    #Add the previous lower bound with the new line source value
                    #Round it to 2 decimals
                    summary = round(sum([float(seen[line2[0]][1]), float(line2[1])]), 2)
                    # Store in dictionary. seen[id] = (id,value,string)
                    # String can be "line1","line2" or "both" depending on where we've seen..
                    # ..it so far. Since this is the second time we encounter the same id we put "both"
                    seen[line2[0]] = ((line2[0]), summary, "both")
                    # If heap has been created and summary > value of the top heap element..
                    # ..and the object isn't already in the heap
                    if flag == 1 and summary > Wk[0][1] and line2[0] not in heap_IDs:
                        heap_IDs.discard(Wk[0][0])#Discard first object id from set
                        Wk.pop(0)#Pop first object from heap
                        heapq.heappush(Wk, seen[line2[0]])#Push the new object in heap whose sum was greater
                        sort_heap()#Sort the heap
                        heap_IDs.add(line2[0])#Add the id of the newly added object to the ID's set
                    elif flag == 1 and summary > Wk[0][1] and line2[0] in heap_IDs:
                        #Search for the index that shows its position in the heap
                        index = next((i for i, tuple in enumerate(Wk) if tuple[0] == line2[0]), -1)
                        #Once found, remove the outdated object.Add the updated version and sort the heap
                        if index != -1:
                            Wk.pop(index)
                            heapq.heappush(Wk, seen[line2[0]])
                            sort_heap()
                else:#If this is the first time we encounter this object ID
                    #Get sum of line value + rnd source value.Round it to 2 decimals
                    summary = round(sum([float(line2[1]), float(R[int(line2[0])])]), 2)
                    #Since this is the first time we encounter this object in seq2, we put "line2"
                    seen[line2[0]] = ((line2[0], summary, "line2"))
                    #If heap hasn't been created yet and we've reached k objects read, create the heap.
                    if flag == 0 and len(seen) == k:
                        declare_min_heap()
                        sort_heap()
                        flag = 1 #Set flag to 1 since the heap has been created
                    elif flag == 1 and summary > Wk[0][1]:#If heap already exists and sum> top element
                        heap_IDs.discard(Wk[0][0])#Remove top object id from the id's set
                        Wk.pop(0)#Remove top object from heap
                        heapq.heappush(Wk, seen[line2[0]])#Add new object
                        sort_heap()#Sort heap
                        heap_IDs.add(line2[0])#Add new object ID to the id's set

                if check_exit(flag, last1, last2, exit) == 1:
                    break

            #If EOF for either seq1 or seq2, break
            if not line1 or not line2:
                break

#Print function
def print_results():
    print("Number of sequential accesses=", sequential_accesses)
    print("Top",k,"objects:")
    for tuple in reversed(Wk):
        print(tuple[0],":", tuple[1])
#Main
if len(sys.argv) == 2: #Check if the user included all the necessary arguments
    try: #Firstly, check if the second argument is an integer
        k = int(sys.argv[1]) #Assign to k-variable the integer value of the second argument
        if (k > 0): #Secondly, check if it is positive
            print()
        else: #Negative integer
            print("Your integer is not positive! Please use a positive integer as your second argument!")
            exit()
    except: #Non-integer second argument
        print("You didn't use an integer as the second argument! Please use a positive integer as your second argument!")
        exit()
else: #Didn't include right amount of arguments
    print("Please use exactly 2 arguments! Your 2nd argument must be a positive integer!")
    exit()
add_from_rnd()
round_robin()
print_results()
