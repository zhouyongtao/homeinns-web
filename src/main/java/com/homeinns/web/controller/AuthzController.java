package com.homeinns.web.controller;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

     /* *
     * 构建OAuth2授权请求 [需要client_id与redirect_uri绝对地址]
     * @param request
     * @param response
     * @return
     * @throws OAuthSystemException
     * @throws IOException
     * @url  http://localhost:8080/oauth2/authorize?client_id={AppKey}&response_type=code&redirect_uri={YourSiteUrl}
     * @test http://localhost:8080/oauth2/authorize?client_id=fbed1d1b4b1449daa4bc49397cbe2350&response_type=code&redirect_uri=http://baidu.comx
     */
    @RequestMapping(value = "/authorize",method = RequestMethod.GET)
    public void authorize(HttpServletRequest request, HttpServletResponse response)
            throws OAuthSystemException, IOException {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            //获得请求的参数信息
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            //验证appkey是否正确
            if (!validateRedirectionURI(oauthRequest)){
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(HttpServletResponse.SC_OK)
                                              .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                                              .setErrorDescription("VERIFY_CLIENTID_FAIL")
                                              .buildJSONMessage();
                out.write(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
          //构建oauth2授权请求
          OAuthResponse oauthResponse = OAuthASResponse
                                        .authorizationResponse(request, HttpServletResponse.SC_FOUND)
                                         //UUIDValueGenerator OR MD5Generator
                                        .setCode(new OAuthIssuerImpl(new MD5Generator()).authorizationCode())
                                        .location(oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI))
                                        .buildQueryMessage();
            response.sendRedirect(oauthResponse.getLocationUri());
        } catch(OAuthProblemException ex) {
            String redirectUri = ex.getRedirectUri();
            //302地址为空
            if (redirectUri==null||redirectUri.length()==0) {
                out.write("oauth2 callback url needs to be provided");
                out.flush();
                out.close();
                return;
            }
            //处理异常
            final OAuthResponse oauthResponse = OAuthASResponse
                                                .errorResponse(HttpServletResponse.SC_FOUND)
                                                .error(ex)
                                                .location(redirectUri)
                                                .buildQueryMessage();
            response.sendRedirect(oauthResponse.getLocationUri());
        }
        finally
        {
            if (null != out){ out.close();}
        }
    }


    /**
     * 获取令牌(AccessToken)
     * @param request
     * @param response
     * @return
     * @url http://localhost:8080/oauth2/access_token?client_id={AppKey}&client_secret={AppSecret}&grant_type=authorization_code&redirect_uri={YourSiteUrl}&code={code}
     */
    @RequestMapping(value = "/access_token",method = RequestMethod.GET)
    public void access_token(HttpServletRequest request, HttpServletResponse response)
            throws IOException, OAuthSystemException {
        PrintWriter out = null;
        OAuthTokenRequest oauthRequest = null;
        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        try {
            out = response.getWriter();
            oauthRequest = new OAuthTokenRequest(request);
            //validateClient(oauthRequest);
            String authzCode = oauthRequest.getCode();
            // some code
            String accessToken = oauthIssuerImpl.accessToken();
            String refreshToken = oauthIssuerImpl.refreshToken();
            // some code

            OAuthResponse r = OAuthASResponse
                              .tokenResponse(HttpServletResponse.SC_OK)
                              .setAccessToken(accessToken)
                              .setExpiresIn("3600")
                              .setRefreshToken(refreshToken)
                              .buildJSONMessage();
            response.setStatus(r.getResponseStatus());
            out.print(r.getBody());
            out.flush();
            out.close();
        } catch(OAuthProblemException ex) {
            OAuthResponse r = OAuthResponse
                    .errorResponse(401)
                    .error(ex)
                    .buildJSONMessage();
            response.setStatus(r.getResponseStatus());
            out.print(r.getBody());
            out.flush();
            out.close();
            response.sendError(401);
        }
        finally
        {
            if (null != out){ out.close();}
        }
    }

    /**
     * 验证ClientID 是否正确
     * @param oauthRequest
     * @return
     */
    public boolean validateRedirectionURI(OAuthAuthzRequest oauthRequest) {
        //客户端Appkey
        ArrayList arrayKeys = new  ArrayList();
        arrayKeys.add("fbed1d1b4b1449daa4bc49397cbe2350");
        arrayKeys.add("a85b033590714fafb20db1d11aed5497");
        arrayKeys.add("d23e06a97e2d4887b504d2c6fdf42c0b");
        return arrayKeys.contains(oauthRequest.getClientId());
    }
}