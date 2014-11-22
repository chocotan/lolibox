Lolibox
=======

一个简单、方便的图床程序

###DEMO
https://box.loli.io

###运行环境
1. Java 7或以上版本
2. Maven(可选)
3. Tomcat 7(可选)

####安装Java
* Java的安装：http://www.java.com/zh_CN/ 点击这个链接，下载安装即可

####运行

下载源码
```
git clone https://github.com/chocotan/lolibox.git
cd lolibox
```
打包运行
```
mvn clean package process-resources tomcat7:exec-war
cd target 
java -jar lolibox-x.y.z-SNAPSHOT-war-exec.jar -httpPort=8080
```
> 第一次执行mvn命令时会下载依赖，如果下载很慢，请下载RELEASE中已经打包好的jar文件，解压修改配置文件后再压缩成jar(zip)文件即可

####相关配置
1. 图片保存位置: 默认保存在[用户工作目录/lolibox/]下，`src/main/resources/config.properties`中可以修改此路径

####预览
http://r.loli.io/2MzeEv.png

http://r.loli.io/EBbyMb.png
