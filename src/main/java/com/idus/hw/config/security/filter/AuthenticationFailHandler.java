package com.idus.hw.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idus.hw.common.exceptions.AuthenticationException;
import com.idus.hw.common.web.WebConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AuthenticationFailHandler {
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException exception) throws IOException {
        this.setResponse(response, exception);
    }

    private void setResponse(HttpServletResponse response,
                             AuthenticationException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(
                        Map.of(
                                WebConstants.ResponseParameter.SUCCESS, false,
                                WebConstants.ResponseParameter.MESSAGE, exception.getMessage()
                        )
                )
        );
    }
}
