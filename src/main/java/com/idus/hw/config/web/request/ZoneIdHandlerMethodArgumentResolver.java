package com.idus.hw.config.web.request;

import com.idus.hw.common.web.WebConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;

/**
 * <a href="org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver">ServletRequestMethodArgumentResolver</a> 와
 * 기능이 중복되기 때문에 이 HandlerMethodArgumentResolver가 먼저 처리되도록 순서를 변경해야한다.
 *
 * @see com.idus.hw.config.web.HandlerMethodArgumentResolverConfiguration
 */
@Slf4j
public class ZoneIdHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ZoneId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        var xTimeZoneHeader = webRequest.getHeader(WebConstants.RequestHeader.X_TIME_ZONE);

        if (xTimeZoneHeader == null) {
            log.warn("Cannot get `{}` header from request", WebConstants.RequestHeader.X_TIME_ZONE);
            return this.findZoneIdFromRequestOrSystemDefault(webRequest);
        }

        try {
            return ZoneId.of(xTimeZoneHeader);
        } catch (ZoneRulesException e) {
            log.warn("Client sent invalid time zone in request header: {}", xTimeZoneHeader);
            return this.findZoneIdFromRequestOrSystemDefault(webRequest);
        }
    }

    private ZoneId findZoneIdFromRequestOrSystemDefault(NativeWebRequest webRequest) {
        var httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        var timeZone = RequestContextUtils.getTimeZone(httpServletRequest);

        if (timeZone == null) {
            log.error("Cannot assume client's time zone");
            return ZoneId.systemDefault();
        }

        return timeZone.toZoneId();
    }
}
