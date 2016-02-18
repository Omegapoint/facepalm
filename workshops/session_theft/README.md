## Session theft
This workshop investigates how to steal a session and taking over a victims logged in state. When logging into an application, the application stores a state which keeps track that the same user is logged in.
In order to identify the user, a cookie is stored at the client with a random value that can be mapped in the application. In a typical Java web application, the session id is stored in a cookie called *JSESSIONID*

All requests after logging in will send the cookie together with the original request. When the server receives the request, it can identify the user based on the session id in the cookie. 
The cookies stored on the client side can often be investigated using the developer tools in your browser.

It is common to add protections on the cookies in an attempt to protect the values from invalid use, such as retrieving cookie values in JavaScript. If not properly protected, cookie values can be retrieved in Javascript using 
```javascript
document.cookie
```

#### Facepalm log
Facepalm has a built-in log endpoint where input data can be stored, viewed and reset. This is not a endpoint a real application would have, but simplifies this workshop. The API (must be logged in) is:
* http://localhost:8080/log/view  **View the log data**
* http://localhost:8080/log/add?data=TEXT **Add new data to log**
* http://localhost:8080/log/reset **Resets log**  

Adding a new log entry using JavaScript has multiple solutions. One of those can be something like
```javascript
var request = new XMLHttpRequest();
request.open('GET', 'http://localhost:8080/add-log?data=tjenareMannen', true);
request.send();
```

#### Stealing the cookies
If an web application has a XSS vulnerability, it is possible to inject code that retrieves the cookie data using JavaScript and then forward that data somewhere where the attacker can retrieve it.

#### Questions
1. What is the cookie representing the session id in Facepalm?
2. How can it be protected against invalid use?
3. Steal a logged in users cookie and switch to that users login. Tip: Use the log for storing secret data