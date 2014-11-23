homeinns-web
============

项目介绍:
---------------
        开发环境: IntelliJ IDEA+ JDK1.8
        
1. Maven构建SpringMVC基础架构





#文档
Maven
-----
http://mvnrepository.com/
Spring
------
http://projects.spring.io/spring-framework/<br/>
http://docs.spring.io/spring/docs/4.0.5.RELEASE/spring-framework-reference/html/spring-web.html
Mybatis
-------
http://mybatis.github.io/spring/zh/index.html
FreeMarker
---------
http://freemarker.org/
Druid
-----
https://github.com/alibaba/druid
Quartz
------
http://quartz-scheduler.org/documentation
Ehcache
-------
http://ehcache.org/documentation/get-started
OsCache
-------
https://java.net/downloads/oscache/
MapDB
-----
http://www.mapdb.org/doc/cheatsheet.pdf
http://www.mapdb.org/02-getting-started.html
Logback
-------
http://logback.qos.ch/manual/index.html

##测试 OAuth2
###获得授权码
http://localhost:8080/oauth2/authorize?client_id=fbed1d1b4b1449daa4bc49397cbe2350&response_type=code&redirect_uri=http://localhost:8080/oauth2/login
###获得令牌
        注意：需要设置HTTP头 Content-Type:application/x-www-form-urlencoded 与 请求方式 POST
http://localhost:8080/oauth2/access_token?client_id=fbed1d1b4b1449daa4bc49397cbe2350&client_secret=fbed1d1b4b1449daa4bc49397cbe2350&grant_type=authorization_code&redirect_uri=http://localhost:8080&code={code}