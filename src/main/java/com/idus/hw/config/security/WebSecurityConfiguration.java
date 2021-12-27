package com.idus.hw.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idus.hw.common.web.WebConstants;
import com.idus.hw.common.web.dto.BaseResponse;
import com.idus.hw.config.security.filter.AuthenticationFailHandler;
import com.idus.hw.config.security.filter.AuthenticationFilter;
import com.idus.hw.config.security.filter.AuthenticationSuccessHandler;
import com.idus.hw.config.security.provider.EmailPasswordAuthenticationProvider;
import com.idus.hw.core.user.application.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthenticationService authenticationService;
    private final Environment environment;

    @Override
    public void configure(WebSecurity web) throws Exception {
        var activeProfiles = environment.getActiveProfiles();

        for (String activeProfile : activeProfiles) {

            if ("local".equals(activeProfile)) {
                web.debug(true);
            }
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().changeSessionId()
                .and()

                .addFilterAfter(this.authenticationFilter(), CsrfFilter.class)

                .authorizeRequests()
                // Swagger UI permission
                .antMatchers("/swagger-ui/index.html", "/swagger-ui/**", "/swagger/**", "/swagger-resources/**", "/v3/api-docs").permitAll()
                // User Join API permission
                .requestMatchers(new OrRequestMatcher(
                        new AntPathRequestMatcher(WebConstants.URL.USER_JOIN_PATH, HttpMethod.POST.name()),
                        new AntPathRequestMatcher(WebConstants.URL.USER_JOIN_PATH_WITH_TRAILING_SLASH, HttpMethod.POST.name())
                )).permitAll()
                .anyRequest().authenticated()
                .and()

                .logout()
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher(WebConstants.URL.LOGOUT_REQUEST_PATH, HttpMethod.POST.name()),
                        new AntPathRequestMatcher(WebConstants.URL.LOGOUT_REQUEST_PATH_WITH_TRAILING_SLASH, HttpMethod.POST.name())
                ))
                .logoutSuccessHandler(((request, response, authentication) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().println(
                            new ObjectMapper().writeValueAsString(BaseResponse.success())
                    );
                }))
                .deleteCookies(WebConstants.Session.COOKIE_NAME);
    }

    private AuthenticationFilter authenticationFilter() throws Exception {
        return new AuthenticationFilter(
                new OrRequestMatcher(
                        new AntPathRequestMatcher(WebConstants.URL.LOGIN_REQUEST_PATH, HttpMethod.POST.name()),
                        new AntPathRequestMatcher(WebConstants.URL.LOGIN_REQUEST_PATH_WITH_TRAILING_SLASH, HttpMethod.POST.name())
                ),
                super.authenticationManager(),
                new AuthenticationSuccessHandler(),
                new AuthenticationFailHandler()
        );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.emailPasswordAuthenticationProvider());
    }

    @Bean
    public EmailPasswordAuthenticationProvider emailPasswordAuthenticationProvider() {
        return new EmailPasswordAuthenticationProvider(this.authenticationService);
    }
}
