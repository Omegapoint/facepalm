## SQL Injection
It is possible to perform SQL injections at multiple places in the applications. An useful query can be

' OR 1=1; --'

The vulnerability can be investigated for example in JPAUserRepository or JPAImageRepository.java. Try to study the code first and then try exploiting the application

## SQL Map
SQL is a utility that can be used to perform automatic advanced SQL attacks against a system.
A tool that might come handy is SQLMap. The image details page looks suitable.