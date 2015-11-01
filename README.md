Lolibox [![Build Status](https://buildhive.cloudbees.com/job/chocotan/job/lolibox/badge/icon)](https://buildhive.cloudbees.com/job/chocotan/job/lolibox/)
=======

A simple, fast image hosting software

###DEMO
http://hime.io/

###Environment
Java 1.6 or above


###Run

####Configuration

Config file `application.properties` is in `lolibox/src/main/resources/`

```
## Server port
server.port=8888
## Server address
# server.address=

## Max upload size
multipart.max-file-size=20MB

## Advanced admin page
## management.context-path=/admin/

## Where images save
imageFolder=/home/choco/lolibox/

## Your email address
email=
## Passwd for login
password=admin
## CDN address
# cdnHost=

```


###Build from github
####Environment
1. Java 1.8
2. Maven 3

####Commands
```
git clone https://github.com/chocotan/lolibox.git
cd lolibox
mvn clean package
./target/lolibox-x.y.z-SNAPSHOT.jar
```

Open `http://host:port/admin.html` and login with email/password in application.properties
to manage images uploaded

####Screenshots
http://r.loli.io/2MzeEv.png
