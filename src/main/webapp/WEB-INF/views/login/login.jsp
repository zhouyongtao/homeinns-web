<%--
  Created by IntelliJ IDEA.
  User: ytzhou
  Date: 2014/7/8
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
</head>
<body>
<form action="/signin" method="POST">
    客户端</br>
    <input type="text" name="name" value="Irving" /> </br>
    <input type="password" name="pwd" value="123456"/></br>
    <a href="/oauth2/authorize?client_id=fbed1d1b4b1449daa4bc49397cbe2350&response_type=code&redirect_uri=http://localhost:8080">
        OAuth2登录
    </a></br>
    <input type="submit" value="登录"/>
</form>
</body>
</html>
