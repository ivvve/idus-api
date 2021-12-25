package com.idus.hw.core.user.domain.user.entity;

import com.idus.hw.common.jpa.BaseEntity;
import com.idus.hw.common.validation.checker.EmailFormatChecker;
import com.idus.hw.common.validation.checker.PhoneNumberFormatChecker;
import com.idus.hw.common.validation.checker.UserNameFormatChecker;
import com.idus.hw.common.validation.checker.UserNicknameFormatChecker;
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

    // nullable
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder
    private User(String email, String encodedPassword,
                 String name, String nickname, String phoneNumber,
                 Gender gender) {
        this.validateAndSetEmail(email);
        this.validateAndSetEncodedPassword(encodedPassword);
        this.validateAndSetName(name);
        this.validateAndSetNickname(nickname);
        this.validateAndSetPhoneNumber(phoneNumber);
        this.setGender(gender);
    }

    private void validateAndSetEmail(String email) {
        if (!EmailFormatChecker.check(email)) {
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

    private void validateAndSetName(String name) {
        if (!UserNameFormatChecker.check(name)) {
            throw new UserDomainValueException("User.name", name);
        }

        this.name = name;
    }

    private void validateAndSetNickname(String nickname) {
        if (!UserNicknameFormatChecker.check(nickname)) {
            throw new UserDomainValueException("User.nickname", nickname);
        }

        this.nickname = nickname;
    }

    private void validateAndSetPhoneNumber(String phoneNumber) {
        if (!PhoneNumberFormatChecker.check(phoneNumber)) {
            throw new UserDomainValueException("User.phoneNumber", phoneNumber);
        }

        this.phoneNumber = phoneNumber;
    }

    private void setGender(Gender gender) {
        this.gender = gender;
    }
}

