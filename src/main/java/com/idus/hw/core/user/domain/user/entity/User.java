package com.idus.hw.core.user.domain.user.entity;

import com.idus.hw.common.jpa.BaseEntity;
import com.idus.hw.common.validation.validator.EmailFormatValidator;
import com.idus.hw.core.user.domain.user.exceptions.UserDomainValueException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Column(name = "email")
    private String email;

    @Column(name = "encoded_password")
    private String encodedPassword;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder
    private User(String email, String encodedPassword) {
        this.validateAndSetEmail(email);
        this.validateAndSetEncodedPassword(encodedPassword);
    }

    private void validateAndSetEmail(String email) {
        if (!EmailFormatValidator.isValid(email)) {
            throw new UserDomainValueException("User.email", email);
        }

        this.email = email;
    }

    private void validateAndSetEncodedPassword(String encodedPassword) {
        if (StringUtils.isBlank(encodedPassword)) {
            throw new UserDomainValueException("User.encodedPassword", email);
        }

        this.encodedPassword = encodedPassword;
    }
}

