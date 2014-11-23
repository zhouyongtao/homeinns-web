package com.homeinns.web.controller;
import com.homeinns.web.common.ConstantKey;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import static javax.servlet.http.HttpServletResponse.SC_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * Authorization Code 授权码模式
 * Created by Irving on 2014/11/22.
 * Impl OAth2  http://oauth.net/2/
 */
@Controller
@RequestMapping("/oauth2")
public class AuthzController {
    private static Logger logger = LoggerFactory.getLogger(AuthzController.class);
    private Cache cache ;
    @Autowired
    public AuthzController(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("oauth2-cache");
    }
     /* *
     * 构建OAuth2授权请求 [需要client_id与redirect_uri绝对地址]
     * @param request
     * @param session
     * @param model
     * @return
     * @throws OAuthSystemException
     * @throws IOException
     * @url  http://localhost:8080/oauth2/authorize?client_id={AppKey}&response_type=code&redirect_uri={YourSiteUrl}
     * @test http://localhost:8080/oauth2/authorize?client_id=fbed1d1b4b1449daa4bc49397cbe2350&response_type=code&redirect_uri=http://baidu.comx
     */
    @RequestMapping(value = "/authorize")
    public String authorize(HttpServletRequest request,HttpSession session,Model model)
            throws OAuthSystemException, IOException {
        try {
            //构建OAuth请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            //验证redirecturl格式是否合法 (8080端口测试)
            if (!oauthRequest.getRedirectURI().contains(":8080")&&!Pattern.compile("^[a-zA-Z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\s*)?$").matcher(oauthRequest.getRedirectURI()).matches()) {
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(SC_UNAUTHORIZED)
                                              .setError(OAuthError.CodeResponse.INVALID_REQUEST)
                                              .setErrorDescription(OAuthError.OAUTH_ERROR_URI)
                                              .buildJSONMessage();
                logger.error("oauthRequest.getRedirectURI() : " + oauthRequest.getRedirectURI() + " oauthResponse.getBody() : " + oauthResponse.getBody());
                model.addAttribute("errorMsg", oauthResponse.getBody());
                return "/oauth2/error";
            }
            //验证appkey是否正确
            if (!validateOAuth2AppKey(oauthRequest)){
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(SC_UNAUTHORIZED)
                                              .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                                              .setErrorDescription(OAuthError.CodeResponse.UNAUTHORIZED_CLIENT)
                                              .buildJSONMessage();
                logger.error("oauthRequest.getRedirectURI() : "+oauthRequest.getRedirectURI()+" oauthResponse.getBody() : "+oauthResponse.getBody());
                model.addAttribute("errorMsg", oauthResponse.getBody());
                return "/oauth2/error";
            }
            //查询客户端Appkey应用的信息
            String clientName= "Just Test App";//oauthClientService.findByClientId(oauthRequest.getClientId());
            model.addAttribute("clientName",clientName);
            model.addAttribute("response_type",oauthRequest.getResponseType());
            model.addAttribute("client_id",oauthRequest.getClientId());
            model.addAttribute("redirect_uri",oauthRequest.getRedirectURI());
            model.addAttribute("scope",oauthRequest.getScopes());
            //验证用户是否已登录
            if(session.getAttribute(ConstantKey.MEMBER_SESSION)==null) {
                //用户登录
                if(!validateOAuth2Pwd(request)) {
                    //登录失败跳转到登陆页
                    return "/oauth2/login";
                }
            }
            //判断此次请求是否是用户授权
            if(request.getParameter("action")==null||!request.getParameter("action").equalsIgnoreCase("authorize")){
                //到申请用户同意授权页
                return "/oauth2/authorize";
            }
           //生成授权码 UUIDValueGenerator OR MD5Generator
           String authorizationCode = new OAuthIssuerImpl(new MD5Generator()).authorizationCode();
           //把授权码存入缓存
           cache.put(authorizationCode,request.getSession(true).getId());
           //构建oauth2授权返回信息
           OAuthResponse oauthResponse = OAuthASResponse
                                         .authorizationResponse(request, SC_FOUND)
                                         .setCode(authorizationCode)
                                         .location(oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI))
                                         .buildQueryMessage();
           //申请令牌成功重定向到客户端页
           return "redirect:"+oauthResponse.getLocationUri();
        } catch(OAuthProblemException ex) {
            OAuthResponse oauthResponse = OAuthResponse
                    .errorResponse(SC_UNAUTHORIZED)
                    .error(ex)
                    .buildJSONMessage();
            logger.error("oauthRequest.getRedirectURI() : " + ex.getRedirectUri() + " oauthResponse.getBody() : " + oauthResponse.getBody());
            model.addAttribute("errorMsg", oauthResponse.getBody());
            return  "/oauth2/error";
        }
    }

    /**
     * 用户登录
     * @param request
     * @return
     */
    private boolean validateOAuth2Pwd(HttpServletRequest request) {
        if("get".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(pwd)) {
            return false;
        }
        try {
            if(name.equalsIgnoreCase("Irving")&&pwd.equalsIgnoreCase("123456")){
                //登录成功
                request.getSession().setAttribute(ConstantKey.MEMBER_SESSION,"Irving");
                return true;
            }
            return false;
        } catch (Exception ex) {
            logger.error("validateOAuth2Pwd Exception: " + ex.getMessage());
            return false;
        }
    }

    /**
     * 验证ClientID 是否正确
     * @param oauthRequest
     * @return
     */
    public boolean validateOAuth2AppKey(OAuthAuthzRequest oauthRequest) {
        //客户端Appkey
        ArrayList arrayKeys = new  ArrayList();
        arrayKeys.add("fbed1d1b4b1449daa4bc49397cbe2350");
        arrayKeys.add("a85b033590714fafb20db1d11aed5497");
        arrayKeys.add("d23e06a97e2d4887b504d2c6fdf42c0b");
        return arrayKeys.contains(oauthRequest.getClientId());
    }
}