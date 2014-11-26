Lolibox [![Build Status](https://buildhive.cloudbees.com/job/chocotan/job/lolibox/badge/icon)](https://buildhive.cloudbees.com/job/chocotan/job/lolibox/)
=======

A simple, fast image hosting software

###DEMO
https://box.loli.io

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

####Optional params

```
[-p PORT] [-a ADDRESS] [-e EMAIL] [-s SAVE]

  -p PORT, --port PORT    http port, default port is 8888
  -a ADDRESS, --address   address to listen on，default is 0.0.0.0
  -e EMAIL, --email EMAIL admin email to show on the bottom of index page
  -s SAVE, --save SAVE    where images save, default is ${user.home}/lolibox
```

###Build from github
####Environment
1. Java 1.6
2. Maven 3

####Commands
```
git clone https://github.com/chocotan/lolibox.git
cd lolibox
mvn clean package assembly:assembly
java -jar target/Lolibox-x.y.z-SNAPSHOT-jar-with-dependencies.jar
```

####Screenshots
http://r.loli.io/2MzeEv.png
http://r.loli.io/EBbyMb.png
