package com.idus.hw.common.web.session;

import com.idus.hw.common.exceptions.AuthenticationException;
import com.idus.hw.common.web.WebConstants;
import com.idus.hw.config.security.authentication.AuthenticatedAuthentication;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpSession;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtils {
    public static void setAuthenticatedAuthentication(HttpSession session,
                                                      AuthenticatedAuthentication authentication) {
        session.setAttribute(WebConstants.Session.ATTRIBUTE_AUTH, authentication);
    }

    public static AuthenticatedAuthentication getAuthenticatedAuthentication(HttpSession session) {
        var authentication = session.getAttribute(WebConstants.Session.ATTRIBUTE_AUTH);

        if (authentication == null) {
            throw new AuthenticationException("Session not contains authentication");
        }

        return (AuthenticatedAuthentication) authentication;
    }
}
