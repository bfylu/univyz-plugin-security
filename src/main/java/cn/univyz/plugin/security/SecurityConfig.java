package cn.univyz.plugin.security;

import cn.univyz.framework.helper.ConfigHelper;
import cn.univyz.framework.util.ReflectionUtil;

/**
 * 从配置文件中获取相关属性
 *
 * @author bfy
 * @version 1.0.0
 */
public final class SecurityConfig {


    public static String getRealms() {
        return ConfigHelper.getString(SecurityConstant.REALMS);
    }

    public static UnivyzSecurity getUnivyzSecurity() {
        String className = ConfigHelper.getString(SecurityConstant.SMART_SECURITY);
        return (UnivyzSecurity) ReflectionUtil.newInstance(className);
    }

    public static String getJdbcAuthcQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
    }

    public static String getJdbcRolesQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
    }
    public static String getJdbcPermissionsQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
    }

    public static boolean isCache(){
        return ConfigHelper.getBoolean(SecurityConstant.CACHE);
    }
}
