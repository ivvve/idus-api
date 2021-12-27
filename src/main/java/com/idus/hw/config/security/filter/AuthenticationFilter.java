package com.idus.hw.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idus.hw.common.exceptions.AuthenticationException;
import com.idus.hw.common.web.WebConstants;
import com.idus.hw.config.security.authentication.AuthenticatedAuthentication;
import com.idus.hw.config.security.authentication.EmailPasswordAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final RequestMatcher requestMatcher;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailHandler authenticationFailHandler;
    private final SessionAuthenticationStrategy sessionAuthenticationStrategy;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!this.requiresAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var authentication = this.attemptAuthentication(request);
            this.sessionAuthenticationStrategy.onAuthentication(authentication, request, response);
            this.authenticationSuccessHandler.handle(request, response, authentication);
        } catch (AuthenticationException e) {
            this.authenticationFailHandler.handle(request, response, e);
        }
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        return this.requestMatcher.matches(request);
    }

    private AuthenticatedAuthentication attemptAuthentication(HttpServletRequest request) {
        var requestBody = this.achieveJSONRequestBody(request);
        log.debug("Authentication request: {}", requestBody);

        if (this.isEmailPasswordAuthenticationRequest(requestBody)) {
            var email = this.getRequestEmail(requestBody);
            var password = this.getRequestPassword(requestBody);
            var emailPasswordAuthentication = new EmailPasswordAuthentication(email, password);

            return (AuthenticatedAuthentication)
                    this.authenticationManager.authenticate(emailPasswordAuthentication);
        }

        throw new AuthenticationException("Fail to achieve Authentication from request");
    }

    private Map<String, String> achieveJSONRequestBody(HttpServletRequest request) {
        try {
            var requestBody = request.getReader().lines()
                    .collect(joining(System.lineSeparator()));
            return new ObjectMapper().readValue(requestBody, Map.class);
        } catch (Exception e) {
            throw new AuthenticationException("Fail to achieve JSON request body", e);
        }
    }

    private boolean isEmailPasswordAuthenticationRequest(Map<String, String> requestBody) {
        var email = this.getRequestEmail(requestBody);
        var password = this.getRequestPassword(requestBody);

        return StringUtils.isNotBlank(email) &&
                StringUtils.isNotBlank(password);
    }

    private String getRequestEmail(Map<String, String> requestBody) {
        return requestBody.get(WebConstants.RequestParameter.LOGIN_EMAIL);
    }

    private String getRequestPassword(Map<String, String> requestBody) {
        return requestBody.get(WebConstants.RequestParameter.LOGIN_PASSWORD);
    }
}
