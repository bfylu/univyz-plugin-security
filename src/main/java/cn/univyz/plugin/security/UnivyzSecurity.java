package cn.univyz.plugin.security;


import java.util.Set;

/**
 * Univyz Securityrukk
 * <br/>
 * 可在应用中实现该接口，或者在univyz.properties文件中提供以下基于SQL 的配置项
 * <ul>
 * <li>univyz.plugin.security.javc.authc_query:根据用户名获取密码</li>
 * <li>univyz.plugin.security.javc.roles_query:根据用户名获取角色名集合</li>
 * <li>univyz.plugin.security.javc.authc_query:根据角色名获取权限名集合</li>
 * </ul>
 *
 * @author bfy
 * @version 1.0.0
 */
public interface UnivyzSecurity {

    /**
     * 根据用户名获取密码
     * @param username 用户名
     * @return 密码
     */
    String getPassword(String username);

    /**
     * 根据用户名获取角色名集合
     * @param username
     * @return 角色名集合
     */
    Set<String> getRoleNameSet(String username);

    /**
     * 根据角色名获取权限名集合
     * @param roleName
     * @return 权限名集合
     */
    Set<String> getPermissionNameSet(String roleName);


}
