Lolibox [![Build Status](https://buildhive.cloudbees.com/job/chocotan/job/lolibox/badge/icon)](https://buildhive.cloudbees.com/job/chocotan/job/lolibox/)
=======

A simple, fast image hosting software

###DEMO
http://hime.io/

###Environment
Java 1.8


###Run

####Configuration

Config file `application.properties` is in `lolibox/src/main/resources/`

```
spring.application.name=lolibox
## Server port
server.port=8080
## Server address
# server.address=
## Max upload size
multipart.max-file-size=20MB
## Advanced management path 
# management.context-path=/admin/
## Your email
email=you@email.com
## Your password
password=admin
## Another path for CDN
# cdnHost=

## storage.type can be 'filesystem', 'aliyun'
storage.type=filesystem
## Where images saved while storage.type is 'filesystem'
storage.filesystem.imageFolder=/home/choco/lolibox/

## aliyun cloud storage settings
#storage.aliyun.url=
#storage.aliyun.key=
#storage.aliyun.secret=
#storage.aliyun.name=
```

####Configuration for Aliyun OSS Cloud Storage

```
storage.type=aliyun

## aliyun cloud storage settings
storage.aliyun.url=aliyun.url
storage.aliyun.key=aliyun.key
storage.aliyun.secret=aliyun.secret
storage.aliyun.name=aliyun.bucketname
```
You should alse uncomment all lines in application.yml.



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

![demo](http://r.loli.io/2MzeEv.png)
