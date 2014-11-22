package com.homeinns.web.controller;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.issuer.UUIDValueGenerator;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Authorization 授权验证
 * Created by Irving on 2014/11/22.
 * Impl OAth2  http://oauth.net/2/
 */
@Controller
@RequestMapping("/oauth2")
public class AuthzController {

    private static Logger logger = LoggerFactory.getLogger(AuthzController.class);

    /**
     * 构建OAuth2授权请求 [需要client_id与redirect_uri绝对地址]
     * @param request
     * @param response
     * @throws OAuthSystemException
     * @throws IOException
     * @url  http://localhost:8080/oauth2/authorize?client_id={AppKey}&response_type=code&redirect_uri={YourSiteUrl}
     * @test http://localhost:8080/oauth2/authorize?client_id=fbed1d1b4b1449daa4bc49397cbe2350&response_type=code&redirect_uri=http://baidu.com
     */
    @RequestMapping(value = "/authorize", method =
            RequestMethod.GET)
    public Object authorize(HttpServletRequest request, HttpServletResponse response)
            throws OAuthSystemException, IOException {
        try {
            //获得请求的参数信息
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            //验证appkey是否正确
            if (!validateRedirectionURI(oauthRequest)){
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(HttpServletResponse.SC_OK)
                                              .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                                              .setErrorDescription("VERIFY_CLIENTID_ERROR")
                                              .buildJSONMessage();
                return new ResponseEntity(oauthResponse.getBody(), HttpStatus.valueOf(oauthResponse.getResponseStatus()));
            }
          //构建oauth2授权请求
          OAuthResponse oauthResponse = OAuthASResponse
                               .authorizationResponse(request, HttpServletResponse.SC_FOUND)
                                //UUIDValueGenerator OR MD5Generator
                               .setCode(new OAuthIssuerImpl(new UUIDValueGenerator()).authorizationCode())
                               .location(oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI))
                               .buildQueryMessage();
           return "redirect:"+oauthResponse.getLocationUri();
        } catch(OAuthProblemException ex) {
            String redirectUri = ex.getRedirectUri();
            if (redirectUri==null||redirectUri.length()==0) {
                return new ResponseEntity(
                        "OAuth callback url needs to be provided !", HttpStatus.NOT_FOUND);
            }
            final OAuthResponse oauthResponse = OAuthASResponse
                                                .errorResponse(HttpServletResponse.SC_FOUND)
                                                .error(ex)
                                                .location(redirectUri)
                                                .buildQueryMessage();
             return "redirect:"+oauthResponse.getLocationUri();
        }
    }

    /**
     * 验证ClientID 是否正确
     * @param oauthRequest
     * @return
     */
    public boolean validateRedirectionURI(OAuthAuthzRequest oauthRequest) {
        //客户端Appkey
        ArrayList clientKey = new  ArrayList();
        clientKey.add("fbed1d1b4b1449daa4bc49397cbe2350");
        clientKey.add("a85b033590714fafb20db1d11aed5497");
        clientKey.add("d23e06a97e2d4887b504d2c6fdf42c0b");
        return clientKey.contains(oauthRequest.getClientId());
    }
}