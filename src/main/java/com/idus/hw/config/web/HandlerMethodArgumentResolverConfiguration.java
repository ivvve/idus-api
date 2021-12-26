package com.idus.hw.config.web;

import com.idus.hw.config.web.request.LoggedInUserHandlerMethodArgumentResolver;
import com.idus.hw.config.web.request.ZoneIdHandlerMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class HandlerMethodArgumentResolverConfiguration {
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public void setHandlerMethodArgumentResolvers() {
        // 요청 처리 시 Custom HandlerMethodArgumentResolver를 먼저 수행하도록 순서 변경
        var reorderedResolvers = new ArrayList<HandlerMethodArgumentResolver>();
        reorderedResolvers.add(new LoggedInUserHandlerMethodArgumentResolver());
        reorderedResolvers.add(new ZoneIdHandlerMethodArgumentResolver());
        reorderedResolvers.addAll(this.requestMappingHandlerAdapter.getArgumentResolvers());

        this.requestMappingHandlerAdapter.setArgumentResolvers(
                Collections.unmodifiableList(reorderedResolvers)
        );
    }
}
