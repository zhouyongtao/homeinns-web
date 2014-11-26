package com.homeinns.web.controller;
import com.homeinns.web.common.ConstantKey;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Irving on 2014/11/22.
 */
@RestController
@RequestMapping("/oauth2")
public class TokenController {
    private static Logger logger = LoggerFactory.getLogger(AuthzController.class);
    private Cache cache ;
    @Autowired
    public TokenController(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("oauth2-cache");
    }

    /**
     * 认证服务器申请令牌(AccessToken)
     * @param request
     * @param response
     * @return
     * @url http://localhost:8080/oauth2/access_token?client_id={AppKey}&client_secret={AppSecret}&grant_type=authorization_code&redirect_uri={YourSiteUrl}&code={code}
     */
    @RequestMapping(value = "/access_token",method = RequestMethod.POST)
    public void access_token(HttpServletRequest request, HttpServletResponse response)
            throws IOException, OAuthSystemException {
        PrintWriter out = null;
        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        try {
            out = response.getWriter();
            //构建oauth2请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            //验证redirecturl格式是否合法 (8080端口测试)
            if (!oauthRequest.getRedirectURI().contains(":8080")&&!Pattern.compile("^[a-zA-Z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\s*)?$").matcher(oauthRequest.getRedirectURI()).matches()) {
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                              .setError(OAuthError.CodeResponse.INVALID_REQUEST)
                                              .setErrorDescription(OAuthError.OAUTH_ERROR_URI)
                                              .buildJSONMessage();
                out.write(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
            //验证appkey是否正确
            if (!validateOAuth2AppKey(oauthRequest)){
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                              .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                                              .setErrorDescription(OAuthError.CodeResponse.UNAUTHORIZED_CLIENT)
                                              .buildJSONMessage();
                out.write(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
            //验证客户端安全AppSecret是否正确
            if (!validateOAuth2AppSecret(oauthRequest)) {
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                              .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                                              .setErrorDescription(ConstantKey.INVALID_CLIENT_SECRET)
                                              .buildJSONMessage();
                out.write(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
            String authzCode = oauthRequest.getCode();
            //验证AUTHORIZATION_CODE , 其他的还有PASSWORD或REFRESH_TOKEN
            if (GrantType.AUTHORIZATION_CODE.equals(oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE))) {
                if (cache.get(authzCode) == null) {
                    OAuthResponse oauthResponse = OAuthASResponse
                                                  .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                                                  .setError(OAuthError.TokenResponse.INVALID_GRANT)
                                                  .setErrorDescription(ConstantKey.INVALID_CLIENT_GRANT)
                                                  .buildJSONMessage();
                    out.write(oauthResponse.getBody());
                    out.flush();
                    out.close();
                    return;
                }
            }
            //清除授权码 确保一个code只能使用一次
            cache.evict(authzCode);
            //生成token
            final String accessToken = oauthIssuerImpl.accessToken();
            cache.put(accessToken,cache.get(authzCode).get());
            String refreshToken = oauthIssuerImpl.refreshToken();
            logger.info("accessToken : "+accessToken +"  refreshToken: "+refreshToken);
            //构建oauth2授权返回信息
            OAuthResponse oauthResponse = OAuthASResponse
                                          .tokenResponse(HttpServletResponse.SC_OK)
                                          .setAccessToken(accessToken)
                                          .setExpiresIn("3600")
                                          .setRefreshToken(refreshToken)
                                          .buildJSONMessage();
            response.setStatus(oauthResponse.getResponseStatus());
            out.print(oauthResponse.getBody());
            out.flush();
            out.close();
        } catch(OAuthProblemException ex) {
            OAuthResponse oauthResponse = OAuthResponse
                                          .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                          .error(ex)
                                          .buildJSONMessage();
            response.setStatus(oauthResponse.getResponseStatus());
            out.print(oauthResponse.getBody());
            out.flush();
            out.close();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
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
    public boolean validateOAuth2AppKey(OAuthTokenRequest oauthRequest) {
        //客户端Appkey
        ArrayList arrayKeys = new  ArrayList();
        arrayKeys.add("fbed1d1b4b1449daa4bc49397cbe2350");
        arrayKeys.add("a85b033590714fafb20db1d11aed5497");
        arrayKeys.add("d23e06a97e2d4887b504d2c6fdf42c0b");
        return arrayKeys.contains(oauthRequest.getClientId());
    }


    /**
     * 验证AppSecret 是否正确
     * @param oauthRequest
     * @return
     */
    public boolean validateOAuth2AppSecret(OAuthTokenRequest oauthRequest) {
        //客户端AppSecret
        ArrayList arrayKeys = new  ArrayList();
        arrayKeys.add("fbed1d1b4b1449daa4bc49397cbe2350");
        arrayKeys.add("a85b033590714fafb20db1d11aed5497");
        arrayKeys.add("d23e06a97e2d4887b504d2c6fdf42c0b");
        return arrayKeys.contains(oauthRequest.getClientSecret());
    }
}