package com.idus.hw.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idus.hw.common.web.WebConstants;
import com.idus.hw.config.security.authentication.AuthenticatedAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AuthenticationSuccessHandler {
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AuthenticatedAuthentication authentication) throws IOException {
        this.setSession(request, authentication);
        this.setResponse(response);
    }

    private void setSession(HttpServletRequest request,
                            AuthenticatedAuthentication authentication) {
        var session = request.getSession(true);
        session.setAttribute(WebConstants.Session.ATTRIBUTE_AUTH, authentication);
    }

    private void setResponse(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter()
                .println(new ObjectMapper().writeValueAsString(
                        Map.of(
                                WebConstants.ResponseParameter.SUCCESS, true,
                                WebConstants.ResponseParameter.MESSAGE, "ok"
                        )
                ));
    }
}
