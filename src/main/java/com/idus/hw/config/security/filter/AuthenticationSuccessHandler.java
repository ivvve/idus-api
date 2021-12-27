package com.idus.hw.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idus.hw.common.web.dto.BaseResponse;
import com.idus.hw.common.web.session.SessionUtils;
import com.idus.hw.config.security.authentication.AuthenticatedAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationSuccessHandler {
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AuthenticatedAuthentication authentication) throws IOException {
        this.setSecurityContextAuthentication(authentication);
        this.setSession(request, authentication);
        this.setResponse(response);
    }

    private void setSecurityContextAuthentication(AuthenticatedAuthentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void setSession(HttpServletRequest request,
                            AuthenticatedAuthentication authentication) {
        var session = request.getSession(true);
        SessionUtils.setAuthenticatedAuthentication(session, authentication);
    }

    private void setResponse(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(BaseResponse.success())
        );
    }
}
