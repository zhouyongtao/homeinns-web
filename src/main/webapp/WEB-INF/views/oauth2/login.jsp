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
    <link type="text/css" href="https://api.weibo.com/oauth2/css/oauthV3/oauth_web.css?version=20140625" rel="stylesheet" />
    <!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"/>-->
    <script type="text/javascript">
         window.onload=function(){
             window.login=function(){
                 var authForm= document.getElementById("authZForm");
                 authForm.submit();
             }
         }
    </script>
</head>
<body class="WB_UIbody WB_widgets">
<div class="WB_xline1 oauth_xline" id="outer">
    <div class="oauth_wrap">
        <div class="oauth_header clearfix">
            <!--  <h1 class="WB_logo" title="微博"><a href="http://weibo.com">微博</a></h1>-->
             <p class="login_account">
                 <!-- <a href="###" class="special_login_link" node-type="loginswitch">二维码登录</a>
                <span class="vline vline_login">|</span>-->
                 <a class="sign_up_link" href="#" target="_blank">注册</a>
            </p>
        </div>
        <!-- 带头像  -->
        <div class="WB_panel oauth_main">
            <form id="authZForm" name="authZForm" action="/oauth2/authorize" method="post" node-type="form">
                <div class="oauth_content" node-type="commonlogin">
                    <p class="oauth_main_info">使用你的帐号访问  <a href="http://app.weibo.com/t/feed/37xdvD"  target="_blank" class="app_name">${clientName}</a>
                        ，并同时登录</p>
                    <!-- 登录 -->
                    <div class="oauth_login clearfix">
                        <input type="hidden" name="action"  id="action" value="login"/>
                        <input type="hidden" name="response_type" value="${response_type}"/>
                        <input type="hidden" name="redirect_uri" value="${redirect_uri}"/>
                        <input type="hidden" name="client_id" value="${client_id}"/>
                        <input type="hidden" name="scope" id="scope" value="${scope}"/>
                        <div class="oauth_login_form">
                            <p class="oauth_login_01" >
                                <label class="oauth_input_label">帐号：</label>
                                <input type="text" class="WB_iptxt oauth_form_input" id="name" name="name"  value="Irving" autocomplete="off" tabindex="1" />
                            </p>
                            <p>
                                <label class="oauth_input_label">密码：</label>
                                <input type="password" class="WB_iptxt oauth_form_input" id="pwd" name="pwd" value="123456" autocomplete="off" tabindex="2"/>
                            </p>
                        </div>
                        <!-- </form> -->
                        <div class="tips WB_tips_yls WB_oauth_tips" node-type="tipBox" style="display:none">
                            <span class="WB_tipS_err"></span><span class="WB_sp_txt" node-type="tipContent" ></span>
                            <span class="arr" node-type="tipArrow"></span>
                            <a href="javascript:;" class="close" node-type="tipClose"></a>
                        </div>
                    </div>
                    <div class="oauth_login_box01 clearfix">
                        <div class="oauth_login_submit">
                            <p class="oauth_formbtn"><a onclick="return login();" node-type="submit" tabindex="4" action-type="submit"  href="javascript:void(0)" class="WB_btn_login formbtn_01"></a><a node-type="cancel" tabindex="5" href="javascript:;" action-type="cancel" class="WB_btn_cancel"></a></p>
                        </div>
                        <!-- todo 添加appkey 白名单判断 -->
                    </div>
                    <!-- /登录 -->
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>