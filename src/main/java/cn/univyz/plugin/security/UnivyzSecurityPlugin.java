package cn.univyz.plugin.security;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;


/**
 * Univyz Security 插件
 *
 * @author bfy
 * @version 1.0.0
 */
public class UnivyzSecurityPlugin implements ServletContainerInitializer{

    public void onStartup(Set<Class<?>> handlesTypes, ServletContext servletContext) throws ServletException{
        //设置初始化参数
        servletContext.setInitParameter("shiroConfigLocations", "classpath:univyz-security.ini");
        //注册Listener
        servletContext.addListener(EnvironmentLoaderListener.class);
        //注册Filter
        FilterRegistration.Dynamic univyzSecurityFilter = servletContext.addFilter("UnivyzSecurityFilter", UnivyzSecurityFilter.class);
        univyzSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
    }

}
