import mysql.connector
from mysql.connector import Error
import csv
import os

paths = [] #The paths of the csv files will be stored here.

dbName = "projectdb" #The name of the db we will create. Change it to whatever you want your DB to be named.


#FILE PATHS PART

for root,dirs,files in os.walk('G:\\sxolh\\Mathimata online\\ΠΘΤΕΒΔ\\world_bank_files\\'): #Folder path to where the csv files are stored at. Should be changed accordingly.
#It is HIGHLY suggested that all csv files are stored in the same folder so that none is skipped.
        for file in files:
                filename, extension = os.path.splitext(file)
                if extension == '.csv':
                        paths.append('G:\\sxolh\\Mathimata online\\ΠΘΤΕΒΔ\\world_bank_files\\' +filename + extension)


#CONNECTION PART

try: #trying to connect to MySQL.In case something goes wrong, exception will catch it.
        mydb = mysql.connector.connect(
                host="localhost",
                user="root",
                password="ροοτ",
				allow_local_infile=True
        )
        if mydb.is_connected():
                cursor = mydb.cursor()

                try:		
                        cursor.execute("DROP DATABASE " + dbName) #First we drop the DB, in case the DB already exists.
                except Error as e:
                      print("Couldn't drop db. Db does not exist")  
                cursor.execute("CREATE DATABASE " + dbName) #DB creation.
               
                print("Database is created")

except Error as e:
        print("Encountered an error while trying to connect to MySQL :", e)

cursor.execute("USE " + dbName)
cursor.execute("SET GLOBAL sql_mode='';")
cursor.execute("SET GLOBAL local_infile=1;")

#Table creation. Setting InnoDB as storage engine.
cursor.execute("CREATE TABLE country(country_name VARCHAR(255) ,country_code VARCHAR(255),PRIMARY KEY(country_name))ENGINE = INNODB")
cursor.execute("CREATE TABLE measurements(country_name VARCHAR(255),year YEAR(4),indicator VARCHAR(255),measurement DOUBLE DEFAULT NULL,FOREIGN KEY(country_name) REFERENCES country(country_name),PRIMARY KEY(country_name,year,indicator))ENGINE = INNODB")

print(mydb)

#EXTRACTION PART

# "Helpers". Flags serve the purpose of boolean variables, whereas the arrays will be used to store the extracted cell content from the csv files. 
counter = 0
cnts = [] #countries
cnt_codes = [] #country_codes
indicators = []
year = []
meas = [] #measurements
flag = 1
indicatorscounter = 0
for p in paths: #for every csv file path 
        flag2 = 1
        with open(p, mode='r') as inf: #opening n'th file for reading
                print("Extracting values for country no. :" ,counter)
                print("Current open file path: ",p)
                reader = csv.reader(inf, delimiter= ',')
                row_counter = 0       
                for i in reader:
                        column_counter = 0    
                        for j in i:
                                if(row_counter < 60):
                                    if row_counter == 4: #First 4 excel rows are "dead" rows. They contain 0 information needed.
                                            if column_counter >= 4 and column_counter <= 64: #64'th column since country statistics range from 1960-2020 in every single csv file.
                                                    if flag == 1:
                                                            year.append(j) #We use flag here since we need years to be appended only once in our array.The period 1960-2020 is the same for every country statistics spreadsheet.
                                    if row_counter >= 5: #The rest of the lines( >= 5) contain statistic values.
                                            if flag2 == 1: #Flag2 will be used to get country name/code just once. They are stored in column 0/1 respectively.
                                                    if column_counter == 0: #column 0(1st in physical language) contains the country names.
                                                            cnts.append(j)
                                                    if column_counter == 1: #column 1(2nd in physical language) contains the country codes.
                                                            cnt_codes.append(j)
                                                            flag2 = 0 #Isolated new country name/code. No need for this to be equal to 1 till we open a new csv file.
                                            if column_counter == 2: #column 2(3rd in physical language) contains the indicator names.
                                                    indicators.append(j)
                                            if column_counter >= 4 and column_counter <= 64: #These columns are dedicated to statistical values.
                                                            meas.append(j)
                                    column_counter += 1
                        row_counter += 1
        flag =0 #Setting flag to 0. "years" array has been filled.

        counter += 1 
        meas.append(-9999) #Helper value. Has no real significance. Serves as separator between the measurements of 2 different countries.
print("All data have been extracted: ")
print("Country_names :",len(cnts))
print("Years :",len(year))
print("Total measurements :",len(meas))
print("Indicators :",len(indicators))

#CSV FILE CREATION

data = []
ind_counter=0
cnt_counter =0
year_counter =0

with open('G:\\sxolh\\Mathimata online\\ΠΘΤΕΒΔ\\world_bank_files\\final_csv_file.csv', 'w', newline ='') as f:
	writer = csv.writer(f)	
	for i in range(len(meas)):
		data = []
		if year_counter == 61:
			year_counter = 0
			ind_counter += 1
		if meas[i] != -9999:
			data.append(cnts[cnt_counter])
			data.append(year[year_counter])
			data.append(indicators[ind_counter])
			data.append(meas[i])
			writer.writerow(data)
			year_counter += 1
		else:
			if i != (len(meas)-1):
				helper = "INSERT INTO country(country_name,country_code) VALUES (\"" + cnts[cnt_counter] + "\",\"" + cnt_codes[cnt_counter] + "\")" #helper variable, hence its name. Storing MySQL command for inserting name/code of the new country in our "country" table.
				cursor.execute(helper) #Here the command stored in our helper is executed.		
			cnt_counter += 1
cwd = os.getcwd()  # Get the current working directory (cwd)
files = os.listdir(cwd)  # Get all the files in that directory
print("Files in %r: %s" % (cwd, files))
cursor.execute("LOAD DATA LOCAL INFILE 'G:/sxolh/Mathimata\sonline/ΠΘΤΕΒΔ/world_bank_files/final_csv_file.csv' INTO TABLE measurements FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\r\n' (country_name,year,indicator,@vmeasurement) SET measurement = NULLIF(@vmeasurement,'')")
mydb.commit()

			
			
		
		
		
		
	



