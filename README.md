Lolibox
=======

一个简单、方便的图床程序

###DEMO
https://loli.io

###运行环境
1. Java 7或以上版本
2. Maven
3. Tomcat 7(可选)

###运行
####安装Java及Maven

* Java的安装：http://www.java.com/zh_CN/ 点击这个链接，下载安装即可

CMD中执行`java -version`，如果看到输出了版本号，则安装成功
* Maven的安装：http://mirrors.cnnic.cn/apache/maven/maven-3/3.2.3/binaries/apache-maven-3.2.3-bin.zip 点击下载，解压，然后在环境变量里新增`M2_HOME`为解压路径(包含bin目录的那个文件夹)，在`PATH`变量最后新增`解压路径/bin`，CMD中执行`mvn -version`，如果看到输出了版本号，则安装成功

> 各个linux发行版的软件仓库中都有java以及maven(maven3)，请自行安装

####运行

下载源码
```
git clone https://github.com/chocotan/lolibox.git
cd lolibox
```
打包运行
```
mvn clean package process-resources tomcat7:run
```
> 第一次运行会下载各种依赖包，下载速度视网络情况而定，请耐心等待

####相关配置
1. 图片保存位置: 默认保存在[用户工作目录/lolibox/]下，`src/main/resources/config.properties`中可以修改此路径
2. 端口：`pom.xml`中找到`<port>8888</port>`，在此处修改

####预览
http://r.loli.io/2MzeEv.png

http://r.loli.io/EBbyMb.png
