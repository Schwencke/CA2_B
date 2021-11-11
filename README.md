
*Projects which are expected to use this start-code are projects that require all, or most of the following technologies:*
 - *JPA and REST*
- *Testing, including database test*
- *Testing, including tests of REST-API's*
- *CI and CONTINUOUS DELIVERY*

### Preconditions
*In order to use this code, you should have a local developer setup + a "matching" droplet on Digital Ocean as described in the 3. semester guidelines* 

### Getting Started

This document explains how to use this code (build, test and deploy), locally with maven, and remotely with maven controlled by Github actions
 - [How to use](https://docs.google.com/document/d/1rymrRWF3VVR7ujo3k3sSGD_27q73meGeiMYtmUtYt6c/edit?usp=sharing)

This startcode features examplecode for:

  #Endpoints
   - two different endpoints with userauthentication requriements. 'User' and 'Admin'
   - two individual fetch, one to each of the endpoints - so that the functionallty can be viewed at once.
   - a register endpoint, ready with functionallity to register new users, new users are given the user role, new Admins can only be set from within the database
   - a init endpoint, ready to init the database with testusers. init command can only be run once - will throw error. to be removed after project setup.
  
  
  #Examplecode
   - snippits with fetchmetods included - with and without the use of threads/Futures

  #Security
   - Passwords are encrypted upon usercreation using BCrypt
   - JWT are used to authenticate users upon login.
