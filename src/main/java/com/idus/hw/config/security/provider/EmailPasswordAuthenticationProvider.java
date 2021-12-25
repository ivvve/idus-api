package com.idus.hw.config.security.provider;

import com.idus.hw.config.security.authentication.AuthenticatedAuthentication;
import com.idus.hw.config.security.authentication.EmailPasswordAuthentication;
import com.idus.hw.core.user.application.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationService authenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var emailPasswordAuthentication = (EmailPasswordAuthentication) authentication;

        var authenticatedUser = this.authenticationService.authenticate(
                emailPasswordAuthentication.getEmail(),
                emailPasswordAuthentication.getPassword()
        );

        return new AuthenticatedAuthentication(authenticatedUser.getId());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(EmailPasswordAuthentication.class);
    }
}
