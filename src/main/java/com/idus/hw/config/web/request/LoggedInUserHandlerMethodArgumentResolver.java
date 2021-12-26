package com.idus.hw.config.web.request;

import com.idus.hw.common.exceptions.AuthenticationException;
import com.idus.hw.common.web.dto.LoggedInUser;
import com.idus.hw.common.web.session.SessionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class LoggedInUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(LoggedInUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        var httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        var session = httpServletRequest.getSession(false);

        if (session == null) {
            throw new AuthenticationException("User session is not initialized");
        }

        var authentication = SessionUtils.getAuthenticatedAuthentication(session);
        return new LoggedInUser(authentication.getUserId());
    }
}
