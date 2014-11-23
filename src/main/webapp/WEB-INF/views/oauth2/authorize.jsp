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
    <title>确认授权</title>
    <link href="https://api.weibo.com/oauth2/css/oauthV3/oauth_web.css?version=20140625" rel="stylesheet"/>
</head>
    <body class="WB_UIbody WB_widgets">
    <div class="WB_xline1 oauth_xline" id="outer">
        <div class="oauth_wrap">
            <!-- 带头像  -->
            <div class="WB_panel oauth_main">
                <form name="authZForm" action="/oauth2/authorize" method="post" node-type="form">
                    <div class="oauth_content" node-type="commonlogin">
                        <p class="oauth_main_info">使用你的微博帐号访问  <a href="http://app.weibo.com/t/feed/37xdvD"  target="_blank" class="app_name">${clientName}</a>
                            ，并同时登录微博</p>
                        <div class="oauth_login clearfix">
                            <input type="hidden" name="action"  id="action" value="login"/>
                            <input type="hidden" id="display" name="display" value="default"/>
                            <input type="hidden" name="withOfficalFlag"  id="withOfficalFlag" value="0"/>
                            <input type="hidden" name="quick_auth"  id="quick_auth" value="null"/>
                            <input type="hidden" name="withOfficalAccount"  id="withOfficalAccount" value=""/>
                            <input type="hidden" name="scope"  id="scope" value=""/>
                            <input type="hidden" name="ticket" id="ticket" value=""/>
                            <input type="hidden" name="isLoginSina"  id="isLoginSina" value=""/>
                            <input type="submit" style="position:absolute; top:-9999px"/>
                            <input type="hidden" name="response_type" value="code"/>
                            <input type="hidden" name="regCallback" value="https%3A%2F%2Fapi.weibo.com%2F2%2Foauth2%2Fauthorize%3Fclient_id%3D1937916757%26response_type%3Dcode%26display%3Ddefault%26redirect_uri%3Dhttp%3A%2F%2Fwww.huazhu.com%2Fthird%2Fweibologin.aspx%26from%3D%26with_cookie%3D"/>
                            <input type="hidden" name="redirect_uri" value="http://www.huazhu.com/third/weibologin.aspx"/>
                            <input type="hidden" name="client_id" value="1937916757"/>
                            <input type="hidden" name="appkey62" value="37xdvD"/>
                            <input type="hidden" name="state" value=""/>
                            <input type="hidden" name="verifyToken" value="null"/>
                            <input type="hidden" name="from" value=""/>
                            <input type="hidden" name="switchLogin"  id="switchLogin" value="0"/>
                            <div class="tips WB_tips_yls WB_oauth_tips" node-type="tipBox" style="display:none">
                                <span class="WB_tipS_err"></span><span class="WB_sp_txt" node-type="tipContent" ></span>
                                <span class="arr" node-type="tipArrow"></span>
                                <a href="javascript:;" class="close" node-type="tipClose"></a>
                            </div>
                        </div>
                        <div class="oauth_login_box01 clearfix">
                            <div class="oauth_login_submit">
                                <!--确认授权-->
                                <p class="oauth_formbtn"><a node-type="submit" tabindex="4" action-type="submit"  href="javascript:;" class="WB_btn_login formbtn_01"></a><a node-type="cancel" tabindex="5" href="javascript:;" action-type="cancel" class="WB_btn_cancel"></a></p>
                            </div>
                            <!-- todo 添加appkey 白名单判断 -->
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    </body>
</html>