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
    <title>Token</title>
    <link type="text/css" href="https://api.weibo.com/oauth2/css/oauth/oauth_web.css" rel="stylesheet" />
</head>
<body class="WB_widgets">
<!-- 内容区 -->
<div class="oauth_wrap">
    <div class="oauth_header clearfix">
        <!--  <h1 class="WB_logo" title="微博">微博</h1>-->
         <p class="login_account"></p>
     </div>
     <!-- 无头像  -->
    <div class="WB_panel oauth_main">
        <div class="oauth_error">
            <div class="oauth_error_content clearfix">
                <dl class="error_content">
                    <dt>Token</dt>
                    <dd>
                     ${accessToken}
                     ${errorMsg}
                    </dd>
                </dl>
            </div>
            <div class="oauth_copyright"><a href="#">OAUTH2</a>版权所有</div>
        </div>
    </div>
</div>
</body>
</html>