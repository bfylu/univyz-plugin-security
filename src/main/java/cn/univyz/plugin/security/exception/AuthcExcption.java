package cn.univyz.plugin.security.exception;

import org.apache.shiro.authc.AuthenticationException;

public class AuthcExcption extends Throwable {
    public AuthcExcption(AuthenticationException e) {
    }
}
