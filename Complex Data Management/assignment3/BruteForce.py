
#Paths
# rndPath = 'C:\\Users\\vagili\\Desktop\\diaxeirhsh_synthetwn_dedomenwn\\ergasies\\Ergasia3\\rnd.txt'
# seq1Path = 'C:\\Users\\vagili\\Desktop\\diaxeirhsh_synthetwn_dedomenwn\\ergasies\\Ergasia3\\seq1.txt'
# seq2Path = 'C:\\Users\\vagili\\Desktop\\diaxeirhsh_synthetwn_dedomenwn\\ergasies\\Ergasia3\\seq2.txt'

rndPath = 'C:\\Users\\vaGAYgre\\Desktop\\cse\\diaxeirhsh_synthetwn_dedomenwn\\ergasies\\ergasia3\\rnd.txt'
seq1Path = 'C:\\Users\\vaGAYgre\\Desktop\\cse\\diaxeirhsh_synthetwn_dedomenwn\\ergasies\\ergasia3\\seq1.txt'
seq2Path = 'C:\\Users\\vaGAYgre\\Desktop\\cse\\diaxeirhsh_synthetwn_dedomenwn\\ergasies\\ergasia3\\seq2.txt'
resultsPath = 'C:\\Users\\vaGAYgre\\Desktop\\cse\\diaxeirhsh_synthetwn_dedomenwn\\ergasies\\ergasia3\\results.txt'

R = []
S1 = []
S2 = []
results = []

#Add contents of rnd to R array.
def add_from_rnd():
    with open(rndPath, mode='r') as rnd: #Open rnd.txt for reading
        for line in rnd: #For each line
            splitLine = line.split() #Split line to get it's contents. id,rating
            R.append(float(splitLine[1])) #Add to array

def add_from_seq1():
    with open(seq1Path, mode='r') as seq1: #Open rnd.txt for reading
        for line in seq1: #For each line
            splitLine = line.split() #Split line to get it's contents. id,rating
            S1.append([int(splitLine[0]),float(splitLine[1])]) #Add to array

def add_from_seq2():
    with open(seq2Path, mode='r') as seq2: #Open rnd.txt for reading
        for line in seq2: #For each line
            splitLine = line.split() #Split line to get it's contents. id,rating
            S2.append([int(splitLine[0]),float(splitLine[1])]) #Add to array

def calculate_results():
    for i in range(len(R)):
        sum =  round(R[i] + S1[i][1] + S2[i][1],2)
        results.append([i,sum])

def create_results_txt():
    with open(resultsPath, 'w') as res:
        for i in results:
            line = f"{i[0]}: {i[1]}"
            res.write(line + '\n')

add_from_rnd()
add_from_seq1()
add_from_seq2()
S1 = sorted(S1, key=lambda x: x[0])
S2 = sorted(S2, key=lambda x: x[0])
calculate_results()
results = sorted(results, key=lambda x: x[1], reverse=True)
create_results_txt()
