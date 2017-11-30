package cn.univyz.plugin.security.exception;

import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthzException extends AuthenticationException {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthzException.class);
    public AuthzException(String e) {
        LOGGER.error(e);
        throw new AuthenticationException(e);
    }
}
