package com.homeinns.web.controller;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Irving on 2014/11/23.
 * OAuth2 资源服务
 */
@RestController
@RequestMapping("/oauth2")
public class ResourceController {
    private static Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private Cache cache ;
    @Autowired
    public ResourceController(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("oauth2-cache");
    }

    @RequestMapping("/resource")
    public void resource(HttpServletRequest request, HttpServletResponse response)
            throws IOException, OAuthSystemException{
        PrintWriter out = null;
        try {
            out = response.getWriter();
            //构建oauth2资源请求
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            //获取验证accesstoken
            String accessToken = oauthRequest.getAccessToken();
            //验证accesstoken是否存在或过期
            if (accessToken.isEmpty()||cache.get(accessToken)== null) {
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                              .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                                              .setErrorDescription(OAuthError.ResourceResponse.EXPIRED_TOKEN)
                                              .buildJSONMessage();
                out.write(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
            //获得用户名
            String userName = "Irving"; //oAuthService.getNameByAccessToken(accessToken);
            out.print(userName);
            out.flush();
            out.close();
        } catch (OAuthProblemException ex) {
            logger.error("ResourceController OAuthProblemException : "+ex.getMessage());
            //检查是否设置了错误码
            String errorCode = ex.getError();
            if (OAuthUtils.isEmpty(errorCode)) {
                OAuthResponse oauthResponse = OAuthRSResponse
                                              .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                              .setError(OAuthError.ResourceResponse.INVALID_REQUEST)
                                              .buildJSONMessage();
                out.print(oauthResponse.getBody());
                out.flush();
                out.close();
            }
        }
        finally {
            if (null != out){ out.close();}
        }
    }
}