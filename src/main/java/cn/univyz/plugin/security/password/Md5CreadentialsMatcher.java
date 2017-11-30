package cn.univyz.plugin.security.password;


import cn.univyz.framework.util.CodecUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * MD5 密码匹配器
 * @author bfy
 * @version 1.0.0
 */
public class Md5CreadentialsMatcher implements CredentialsMatcher{
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获取从表单提交过来的密码，明文，尚未通过MD5加密
        String submitted = String.valueOf(((UsernamePasswordToken) token).getPassword());
        String encrypted = String.valueOf(info.getCredentials());
        return CodecUtil.md5(submitted).equals(encrypted);
    }
}
