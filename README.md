Lolibox [![Build Status](https://buildhive.cloudbees.com/job/chocotan/job/lolibox/badge/icon)](https://buildhive.cloudbees.com/job/chocotan/job/lolibox/)
=======

A simple, fast image hosting software

###DEMO
http://hime.io/

###Environment
Java 1.6 or above


###Run
####Download
* Download from github [Releases](https://github.com/chocotan/lolibox/releases)
* Download from [百度网盘](http://pan.baidu.com/s/1dDpLenR)

####Commands
```
java -jar Lolibox-x.y.z-SNAPSHOT-jar-with-dependencies.jar
```

* Replace `x.y.z-SNAPSHOT` with the version you downloaded

####Configuration
```
## Server port
server.port=8080
## Server address
# server.address=

## Max upload size
multipart.max-file-size=20MB

## Admin page
## management.context-path=/admin/

## Where images save
imageFolder=/home/choco/lolibox/

## Your email address
email=loli@linux.com
## Passwd for login
password=admin
## CDN address
# cdnHost=https://c.hime.io

```

###Build from github
####Environment
1. Java 1.6
2. Maven 3

####Commands
```
git clone https://github.com/chocotan/lolibox.git
cd lolibox
mvn clean package assembly:single
java -jar target/Lolibox-x.y.z-SNAPSHOT-jar-with-dependencies.jar
```

####Screenshots
http://r.loli.io/2MzeEv.png
