## Command execution
Command execution is a vulnerability where the attacker can perform an arbitrary command on the attacked system. An example of this exploit is when the application reads file from disk using shell commands. Using shell/cmd syntax knowledge, sample attacks could be
1. *nix host
```sh
; cat /etc/passwd
```
2. Windows host
```sh
%26dir