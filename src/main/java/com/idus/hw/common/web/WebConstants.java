package com.idus.hw.common.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebConstants {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class URL {
        public static final String LOGIN_REQUEST_PATH = "/login";
        public static final String LOGIN_REQUEST_PATH_WITH_TRAILING_SLASH = LOGIN_REQUEST_PATH + "/";

        public static final String LOGOUT_REQUEST_PATH = "/logout";
        public static final String LOGOUT_REQUEST_PATH_WITH_TRAILING_SLASH = LOGOUT_REQUEST_PATH + "/";

        public static final String USER_JOIN_PATH = "/users";
        public static final String USER_JOIN_PATH_WITH_TRAILING_SLASH = USER_JOIN_PATH + "/";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RequestParameter {
        public final static String LOGIN_EMAIL = "email";
        public final static String LOGIN_PASSWORD = "password";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResponseParameter {
        public final static String SUCCESS = "success";
        public final static String MESSAGE = "message";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Session {
        public static final String COOKIE_NAME = "JSESSIONID";

        public static final String ATTRIBUTE_AUTH = "auth";
    }
}
