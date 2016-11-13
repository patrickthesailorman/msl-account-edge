package io.swagger.api.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.NewCookie;
import java.util.UUID;

/**
 * @author Kenzan
 */
public interface AccountEdgeSessionToken {
    void setDomain (String domain);
    void updateSessionToken(HttpServletRequest req);
    boolean isValidToken();
    NewCookie getSessionCookie(UUID sessionToken);
    String getTokenValue();
    void setTokenValue(String tokenValue);
}
