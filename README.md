Lolibox 
=======
[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg?style=flat-square)](https://raw.githubusercontent.com/chocotan/lolibox/master/LICENSE)


一个自认为比较精简的图床程序（其实这只是当时刚学springboot后的练手项目）

### 示例站点
https://loli.io/

### 运行环境
Java 1.8

### 从Github构建
#### 编译所需环境
1. Java 1.8
2. Maven 3

#### 编译&运行
```
git clone https://github.com/chocotan/lolibox.git
cd lolibox
mvn clean package
chmod +x ./lolibox-server/target/lolibox-server-x.y.z-SNAPSHOT.jar
./lolibox-server/target/lolibox-server-x.y.z-SNAPSHOT.jar
```

#### 配置说明
```yaml
# 应用名称，保持默认即可
spring.application.name: lolibox
# 运行端口，请自行修改为你所需要的端口
server.port: 8888
# 监听的ip，在没有用nginx等反代的情况下，如果想让外网也能访问，这个请设置为0.0.0.0
server.address: 127.0.0.1
# 图片最大大小
spring.http.multipart.max-file-size: 20MB
# 管理界面context-path，请勿修改
management.context-path: /admin/
# tomcat临时目录，请勿修改
server:
  tomcat:
    basedir: ${user.home}/lolibox/tmp
# spring的配置，请勿修改
security:
  headers:
    cache: true
  ignored: /webjars/**,/css/**,/js/**,/img/**
spring.resources.chain.strategy.content.enabled: true
spring.resources.chain.strategy.content.paths: /**
# 日志文件路径
logging:
  file: ${user.home}/lolibox/lolibox.log
  level.io.loli: debug
  pattern:
    level:
# 管理员配置
admin:
  # 管理员的邮箱，你需要修改这里的配置，并在成功运行后，注册一个这个邮箱的账号
  email: admin@yoursite.com
  # 是否开启匿名上传
  anonymous: false
  # 是否开启注册邀请，具体怎么用请提issue
  signup-invitation: false
  invitation-seed: lolibox
  cdnHost:
  # 如果你使用的是云存储，请把此配置修改为你的云存储的域名，示例：http://mycloud.qcloud.com/
  imgHost:
  green:
    enabled: none
storage:
  # 图片存储服务类型，filesystem表示使用系统磁盘，还支持aliyun、qiniu、baidu、cos
  type: filesystem
  # 图片存储路径，只在type为filesystem时有效
  filesystem.imgFolder: ${user.home}/lolibox/
  aliyun:
    url:
    key:
    secret:
    name:
  qiniu:
    key:
    secret:
    name:
  baidu:
    key:
    secret:
    url:
    name:
  cos:
    key:
    secret:
    url:
    name:
    region:
    bucket:

# 下面都是spring相关的配置，请勿修改
spring:
  h2:
    console:
      enabled: true
      path: /admin/h2-console
  jpa:
    hibernate:
      ddl-auto: update
      naming:
        strategy: io.loli.box.CustomNamingStrategy
  datasource:
    url: jdbc:h2:file:~/lolibox/db;FILE_LOCK=FS
    username: sa
    password: sa
  messages:
    basename: i18n/message

  thymeleaf:
    cache: false

spring.social.weibo:
  app-id:
  app-secret:
---
spring.profiles: zuul
zuul:
  routes:
    images:
      path: /images/**
      url:
```

#### 截图

![example](https://i.loli.net/2018/02/22/5a8e7c2ccfdf6.png)
