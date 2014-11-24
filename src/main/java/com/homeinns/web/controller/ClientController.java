package com.homeinns.web.controller;
import com.homeinns.web.common.ConstantKey;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Irving on 2014/11/24.
 * OAuth2 客户端实现
 */
@Controller
@RequestMapping("/oauth2")
public class ClientController {

    private static Logger logger = LoggerFactory.getLogger(AuthzController.class);
    /**
     * 获得授权码
     * @return
     */
    @RequestMapping(value = "/client" ,method = RequestMethod.GET)
    public String client() {
        try {
            OAuthClientRequest oauthResponse = OAuthClientRequest
                                               .authorizationLocation(ConstantKey.OAUTH_CLIENT_AUTHORIZE)
                                               .setResponseType("code")
                                               .setClientId(ConstantKey.OAUTH_CLIENT_ID)
                                               .setRedirectURI(ConstantKey.OAUTH_CLIENT_REDIRECT)
                                               .buildQueryMessage();
            return "redirect:"+oauthResponse.getLocationUri();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return "redirect:/home";
    }

    /**
     * 获得令牌
     * @return
     */
    @RequestMapping(value = "/getOAuthzToken" ,method = RequestMethod.GET)
    public String oauth2_getToken(HttpServletRequest request,Model model) throws OAuthProblemException {
        OAuthAuthzResponse oauthAuthzResponse = null;
        try {
            oauthAuthzResponse = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
            String code = oauthAuthzResponse.getCode();
            OAuthClientRequest oauthClientRequest = OAuthClientRequest
                                                    .tokenLocation(ConstantKey.OAUTH_CLIENT_ACCESS_TOKEN)
                                                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                                                    .setClientId(ConstantKey.OAUTH_CLIENT_ID)
                                                    .setClientSecret(ConstantKey.OAUTH_CLIENT_SECRET)
                                                    .setRedirectURI(ConstantKey.OAUTH_CLIENT_REDIRECT)
                                                    .setCode(code)
                                                    .buildQueryMessage();
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            //Facebook is not fully compatible with OAuth 2.0 draft 10, access token response is
            //application/x-www-form-urlencoded, not json encoded so we use dedicated response class for that
            //Custom response classes are an easy way to deal with oauth providers that introduce modifications to
            //OAuth 2.0 specification
            OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(oauthClientRequest);
            String accessToken = oAuthResponse.getAccessToken();
            String refreshToken= oAuthResponse.getRefreshToken();
            Long expiresIn = oAuthResponse.getExpiresIn();
            logger.info("accessToken: "+accessToken +" refreshToken: "+refreshToken +" expiresIn: "+expiresIn);
            model.addAttribute("accessToken",  accessToken);
            return "oauth2/token";
        } catch (OAuthSystemException ex) {
            logger.error("getOAuthzToken OAuthSystemException : " + ex.getMessage());
            model.addAttribute("errorMsg",  ex.getMessage());
            return  "/oauth2/error";
        }
    }

    /*
    @RequestMapping(value = "/oauth2_login" ,method = RequestMethod.POST)
    public String oauth2_login(String returnUrl) {
        //获得令牌
        logger.info("LoginController oauth2_login ");
        return "oauth2/login";
    }
    */
}
