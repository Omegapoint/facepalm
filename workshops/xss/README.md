## Cross-site scripting (XSS)
XSS is an attack where an attacker injects client side code into web pages that are viewed by other users. There are different versions of a XSS, the most common ones are
#### Reflected XSS (non-persistent)
Reflected XSS is the most common XSS attack where the server piggy-backs client sent data back to the user, such as HTTP query variables. An example of this is when the user is performing a search on a web page. The search result will then have a text like  
*"Search results for: ATTACK_QUERY"*  

One common use case is providing an attack URL in an email. The link itself might look trustworthy but contains the attack injection as a query parameter.

#### Stored XSS (persistent)
If an application does not validate input data, it is possible to store an XSS attack in the database when sent as user data. For example, a user can send a comment to an article and include harmful code as the comment text instead of real text. The comment is then stored in the database and viewed for all users reading the comments.

This attack is more dangerous than reflected as it does not require any action at all from victims except entering the wrong page. It is therefor vital that harmful XSS code is not delivered from the server.

#### Example attack
There are some areas that can be exploited using stored cross site scripting. A sample code that can be useful is
```html
<script>alert("Hello")</script>
```

## Template rendering (Server side XSS)
Most modern rendering frameworks handles proper output encoding for you. Thymeleaf, a popular server side template engine uses the tag *th:text="value"* for injecting content into an element. This tag also performs proper encoding so that arbitary JavaScript code will not run. 

Facepalm has some areas where the automatic encoding is bypassed.

#### Questions
1. Can you find somewhere where an injection attack is possible?
2. Why is it possible to execute an injection attack at that place?

## Javascript rendering (DOM based XSS)
Modern web applications is leaving the server-side rendering and moving towards a complete standalone frontend application. These applications are often completely based on JavaScript and DOM manipulation using JS. Plain JavaScript does not provide any autmatic enconding help.

#### Questions
##### DOM maniplation is not yet implemented in Facepalm
