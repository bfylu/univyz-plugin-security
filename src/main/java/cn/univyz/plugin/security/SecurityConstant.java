package cn.univyz.plugin.security;

/**
 * 常量接口
 *
 * @author bfy
 * @version 1.0.0
 */
public interface SecurityConstant {

    String REALMS = "univyz.plugin.security.realms";
    String REALMS_JDBC = "jdbc";
    String REALMS_CUSTOM = "custom";

    String SMART_SECURITY = "uvizy.plugin.security.custom.class";

    String JDBC_AUTHC_QUERY = "univyz.plugin.security.jdbc.authc_query";
    String JDBC_ROLES_QUERY = "univyz.plugin.security.jdbc.roles_query";
    String JDBC_PERMISSIONS_QUERY = "univyz.plugin.security.jdbc.permissions_query";

    String CACHE = "univyz.plugin.security.cache";
}
