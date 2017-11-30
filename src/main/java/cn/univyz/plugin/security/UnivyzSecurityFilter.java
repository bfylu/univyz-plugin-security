package cn.univyz.plugin.security;

import cn.univyz.plugin.security.realm.UnivyzCustomRealm;
import cn.univyz.plugin.security.realm.UnivyzJdbcRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 安全过滤器
 *
 * @author bfy
 * @version 1.0.0
 */
public class UnivyzSecurityFilter extends ShiroFilter {

    @Override
    public void init() throws Exception {
        super.init();
        WebSecurityManager webSecurityManager = super.getSecurityManager();
        // 设置 Realm,可同时支持多个Realm ，并按照先后顺序用逗号分割
        setRealms(webSecurityManager);
        // 设置 Cache，用于减少数据库查询次数，降低 I/O 访问
        setCache(webSecurityManager);
    }

    private void setRealms(WebSecurityManager webSecurityManager) {
        //读取 univzy.plugin.security.realms 配置项
        String securityRealms = SecurityConfig.getRealms();
        if (securityRealms != null) {
            // 根据逗号进行拆分
            String[] securityRealmArray = securityRealms.split(",");
            if (securityRealmArray.length > 0){
                //使Realm具备唯一性与顺序性
                Set<Realm> realms = new LinkedHashSet<Realm>();
                for (String securityRealm : securityRealmArray) {
                    if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)) {
                        // 添加基于 JDBC 的Realm，需配置相关SQL查询语句
                        addJdbcRealm(realms);
                    } else if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)) {
                        //添加基于定制化的Realm,需实现univyzSecurity 接口
                        addCustomRealm(realms);
                    }
                }
                RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
                realmSecurityManager.setRealms(realms); //设置Realm
            }
        }
    }

    private void addJdbcRealm(Set<Realm> realms) {
        //添加自己实现的基于JDBC的Ream
        UnivyzJdbcRealm univyzJdbcRealm = new UnivyzJdbcRealm();
        realms.add(univyzJdbcRealm);
    }

    private void addCustomRealm(Set<Realm> realms) {
        //读取Univyz.plugin.security.custom.class 配置项
        UnivyzSecurity univyzSecurity = SecurityConfig.getUnivyzSecurity();
        //添加自己的REalm
        UnivyzCustomRealm univyzCustomRealm = new UnivyzCustomRealm(univyzSecurity);
        realms.add(univyzCustomRealm);
    }

    private void setCache(WebSecurityManager webSecurityManager) {
        //读取 univyz.plugin.security.cache 配置项
        if (SecurityConfig.isCache()) {
            CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
            //使用基于内存的CacheManager
            CacheManager cacheManager = new MemoryConstrainedCacheManager();
            cachingSecurityManager.setCacheManager(cacheManager);
        }
    }
}
