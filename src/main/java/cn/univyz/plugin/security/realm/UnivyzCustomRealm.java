package cn.univyz.plugin.security.realm;

import cn.univyz.plugin.security.SecurityConstant;
import cn.univyz.plugin.security.UnivyzSecurity;
import cn.univyz.plugin.security.password.Md5CreadentialsMatcher;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * 基于Univyz的自定义Realm（需要实现UnivyzSecurity 接口）
 *
 * @author bfy
 * @version 1.0.0
 *
 */
public class UnivyzCustomRealm extends AuthorizingRealm{

    private final UnivyzSecurity univyzSecurity;

    public UnivyzCustomRealm(UnivyzSecurity univyzSecurity) {
        this.univyzSecurity = univyzSecurity;
        super.setName(SecurityConstant.REALMS_CUSTOM);
        super.setCredentialsMatcher(new Md5CreadentialsMatcher()); //使用MD5加密算法
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token == null) {
            throw new AuthenticationException("parameter token is null");
        }
        //通过AuthenticationToken对象获取从表单中提交过来的用户名
        String username = ((UsernamePasswordToken) token).getUsername();

        //通过UnivzySecurity接口并根据用户名获取数据库中存放的密码
        String password = univyzSecurity.getPassword(username);

        //将用户名与密码放入AuthenticationInfo 对象中，便于后续的认证操作
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        authenticationInfo.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
        authenticationInfo.setCredentials(password);
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null){
            throw new AuthorizationException("parameter principals is null");
        }
        //获取已认证用户的用户名
        String username = (String) super.getAvailablePrincipal(principals);

        //通过UnivyzSecutity 接口并根据用户名获取角色名集合
        Set<String> roleNameSet = univyzSecurity.getRoleNameSet(username);

        //通过UnivyzSecurity接口并根据角色名获取与其对应的权限集合
        Set<String> permissionNameSet = new HashSet<String>();
        if (roleNameSet != null && roleNameSet.size() > 0) {
            for (String roleName : roleNameSet) {
                Set<String> currentPermissionNameSet = univyzSecurity.getPermissionNameSet(roleName);
                permissionNameSet.addAll(currentPermissionNameSet);
            }
        }

        //将角色名集合与权限名集合放入AuthorizationInfo对象中，便于后续的授权操作
        SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
        authenticationInfo.setRoles(roleNameSet);
        authenticationInfo.setStringPermissions(permissionNameSet);
        return authenticationInfo;
    }


}
