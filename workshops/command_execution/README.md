## Command execution
Command execution is a vulnerability where the attacker can perform an arbitrary command on the attacked system. The user passes some input in a request which is handled by the server. The server takes the user input and injects it into a query that is executed in a shell. An example of this exploit is when the application reads file from disk using shell *cat* command.   

As an example, we have an application that takes in a filename to be read from the file system. The user passes the filename in the request and the application injects the filename into a cat command. The executed command would then be
```sh
cat filename.txt
```

Using shell/cmd syntax knowledge, sample attacks could be
1. *nix host
```sh
filename.txt; cat /etc/passwd
```
2. Windows host
```sh
filename.txt%26dir
```

#### Complete example
```http
http://localhost:8080/view?file=document.txt; cat /etc/passwd
```
