#### Please note that all URLs are examples, you need to find new URLs in the application

## SQL Injection
A SQL injection attack is performed when an attacker attempts to pass bad data to the application in hope that it will be injected into a SQL query.
For example, when attempting to login, it can be guessed that the query should look somewhat like  
*"SELECT * FROM USERS WHERE USERNAME='" + username + "' AND PASSWORD='" + password + "'"*

If the attacker would then enter the username admin and password helloworld, the complete query would be  
*"SELECT * FROM USERS WHERE USERNAME='admin' AND PASSWORD='helloworld'"*

By realising how a query is built, the attacker could instead pass username admin and password ' OR 1=1; --'

#### Questions
1. What would the resulting query be for the injection attack?
2. What does the attack query do and how can it be used?
3. Can you find any place in the application where you can use this attack?
4. How can you fix it in the code?

## Display information using SQL injection
Sometimes information is retrieved from a SQL query and displayed in the client.
If there is a sql vulnerability, this can be exploited into showing unintended data, such as internal database information

This is most common in GET requests where information is retrieved based a parameter, such as user id. Let's take the example where grades are retrieved by user id.  
*Endpoint: .../grades?user=admin*  
*Resulting backend query: "SELECT * FROM GRADES WHERE USERNAME='admin'"*

If the attacker would then instead change the user to "admin' OR 1=1 --'", the result would be  
*Endpoint: .../grades?user=' OR 1=1 --'*  
*Resulting backend query: "SELECT * FROM GRADES WHERE USERNAME='admin' OR 1=1 --''"*

If the page load is properly loaded, there is a high risk for a SQL vulnerability. We can then check how many columns the queried table has:  
*.../grades?user=admin' ORDER BY 3 --'*   **# Page loads correctly, sorted according the 3 column**  
*.../grades?user=admin' ORDER BY 5 --'*   **# Page loads correctly, sorted according the 5 column**  
*.../grades?user=admin' ORDER BY 6 --'*   **# Fails! We only have 5 columns**  

We can then query the type of each column by selecting values that we think are matching types  
*.../grades?user=admin' UNION SELECT null, null, null, null, null --'*   **# Start with only null, change one at a time**  
*.../grades?user=admin' UNION SELECT 1, null, 3, 'TEST', 'ABC' --'*      **# Change data types as long as server does not crash**

#### Questions
1. What could be interesting to list in such attacks?
2. How would an attack SQL query look like?
3. Is there any special metadata that can be queried for important information?
4. Can you find a page with this weakness in the application?
5. How do you solve it?

## SQL Map - Automatic injection tool
In real life, attackers will not manually test and inject on apges. Instead tools are used to automatically detect vulnerabilities and perform attacks against these. There are more advanced injection attacks, such as time-based, where a tool is a requirement. SQLMap (http://sqlmap.org/) is a python utility that can be used to perform automatic advanced SQL attacks against a system.

SQLMap first attempts to check if an endpoint is vulnerable. If so, it attempts to detect which database is used for persisting data. After that, information is extracted using special queries. The user manual for SQLMap can be found at https://github.com/sqlmapproject/sqlmap/wiki/Usage

#### Usage
First, a vulnerable endpoint needs to be found. An endpoint can be tested using
```python
python sqlmap.py -u "http://localhost:8080/....?id=123"
```
However, in a lot of cases the vulnerable endpoint is at a place where the user needs to be logged in. If so, we also need to send a cookie value for the logged in session as SQLMap will run separate from your browser.
```python
python sqlmap.py -u "http://localhost:8080/....?id=123" --cookie="JSESSIONID=E52B5F51105364280C3E011C5EA837E8"
```
If vulnerable, the running database will be detected and the user will be notified that an attack is possible. If an vulnerable parameter is detected, there is no need to continue searching for more. Let's continue investigate which schemas/databases are available
```python
python sqlmap.py -u "http://localhost:8080/....?id=123" --cookie="JSESSIONID=E52B5F51105364280C3E011C5EA837E8" --dbs --no-cast
```
*dbs* will attempt to read all databases. *--no-cast* is only needed when detecting schemas and the underlying database is an embedded HSQLDB as in Facepalm - normally it can be left out. Once the schemas are found, the tables in a specific schema can be found as
```python
python sqlmap.py -u "http://localhost:8080/....?id=123" --cookie="JSESSIONID=E52B5F51105364280C3E011C5EA837E8" -D #SCHEMA_NAME# --tables
```
where *#SCHEMA_NAME#* is replaced with the real schema name. There are of course more attacks to perform which are asked for in the *Questions* section.

#### Questions
1. How can SQLMap detect if an endpoint is vulnerable?
2. How does SQLMap detect which database is running?
3. How can SQLMap read and dump the complete database without any prior knowledge except detected database software?
4. Can you find somewhere in Facepalm where you can run SQLMap?
5. Detect all available schemas and tables
6. Read the content of a specific table
6. Dump the database using the found endpoint in 4) and show the user information

## (Solutions)
The vulnerability can be investigated for example in JPAUserRepository or JPAImageRepository.java. Try to study the code first and then try exploiting the application

