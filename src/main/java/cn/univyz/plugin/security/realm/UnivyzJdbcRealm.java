package cn.univyz.plugin.security.realm;

import cn.univyz.framework.helper.DatabaseHelper;
import cn.univyz.plugin.security.SecurityConfig;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.realm.jdbc.JdbcRealm;

/**
 * 基于 Univyz 的 JDBC Realm(需要提供相关 univyz.plugin.security.jdbc.* 配置项
 *
 * @author bfy
 * @version 1.0.0
 */
public class UnivyzJdbcRealm extends JdbcRealm {

    public UnivyzJdbcRealm() {
        super.setDataSource(DatabaseHelper.getDataSource());
        super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
        super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
        super.setPermissionsQuery(SecurityConfig.getJdbcPermissionsQuery());
        super.setPermissionsLookupEnabled(true);
        super.setCredentialsMatcher(new Md5CredentialsMatcher()); //使用MD5 加密算法

    }
}
