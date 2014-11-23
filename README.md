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

####命令
将如下环境变量中的值改为你想要的
  LOLIBOX_SAVE_PATH：图片保存目录，必须可写
  LOLIBOX_ADMIN_EMAIL：页面底部的“联系我”链接

将命令中其中`x.y.z-SNAPSHOT`修改为你所下载jar的版本号，`httpPort`参数为http端口号

######Windows 
```
set LOLIBOX_SAVE_PATH=C:\SAVE\PATH
set LOLIBOX_ADMIN_EMAIL=you@email.com
java -jar lolibox-x.y.z-SNAPSHOT-war-exec.jar -httpPort=8080
```
######Linux
```
EXPORT LOLIBIX_SAVE_PATH=/save/path
EXPORT LOLIBOX_ADMIN_EMAIL=you@email.com
java -jar lolibox-x.y.z-SNAPSHOT-war-exec.jar -httpPort=8080
```


###从源码编译最新版
编译环境
1. Java 7
2. Maven 3

图片保存目录 和 email可以在`src/main/resources/config.properties`文件中设置，这里的设置会覆盖环境变量

运行以下命令
```
git clone https://github.com/chocotan/lolibox.git
cd lolibox
mvn clean package process-resources tomcat7:exec-war
```
这样就在target目录中生成最新的可执行jar文件了


####预览
http://r.loli.io/2MzeEv.png
http://r.loli.io/EBbyMb.png
