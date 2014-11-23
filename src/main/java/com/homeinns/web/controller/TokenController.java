package com.homeinns.web.controller;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Irving on 2014/11/22.
 */
@RestController
public class TokenController {

    private static Logger logger = LoggerFactory.getLogger(AuthzController.class);

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
}
