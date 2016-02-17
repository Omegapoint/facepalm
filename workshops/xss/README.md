## Template rendering
There are some areas that can be exploited using stored cross site scripting. A sample code that can be useful is
```html
<script>alert("Hello")</script>
```

Most modern rendering frameworks handles proper output encoding for you. However, some field have the automatic encoding deactivated.

## Javascript rendering
TBD.