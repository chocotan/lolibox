Lolibox
=======

一个简单、方便的图床程序

###DEMO
https://box.loli.io

###运行环境
Java 7或以上版本

> Java的安装：点击[这里](http://www.java.com/zh_CN/)下载安装

###运行
####下载RELEASE版本
目前最新版是0.0.2-RELEASE，下载见 [百度网盘](http://pan.baidu.com/s/12c0Uy)

#####运行方式
```
java -jar lolibox-x.y.z-SNAPSHOT-war-exec.jar -httpPort=8080
```
将其中`x.y.z`修改为你所下载jar的版本号，`httpPort`参数为http端口号


###从源码编译最新版
编译环境
1. Java 7
2. Maven 3

运行以下命令
```
git clone https://github.com/chocotan/lolibox.git
cd lolibox
mvn clean package process-resources tomcat7:exec-war
```

####相关配置
1. 从源码编译运行的直接修改`src/main/resources/config.properties`
2. 直接运行的 解压后找到`.war`文件，再将其解压到其他目录，找到`.war/WEB-INF/classes/config.properties`，修改后再将其以`zip`格式打包，替换原文件（先替换.war，再将整个打包成.jar后缀的文件），然后执行运行命令

####预览
http://r.loli.io/2MzeEv.png

http://r.loli.io/EBbyMb.png
