package cn.univyz.plugin.security.aspect;

import cn.univyz.framework.annotation.Aspect;
import cn.univyz.framework.annotation.Controller;
import cn.univyz.plugin.security.annotation.User;
import cn.univyz.plugin.security.exception.AuthzException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 授权注解切面
 *
 * @author bfy
 * @version 1.0.0
 */
@Aspect(Controller.class)
public class AuthzAnnotationAspect {
    /**
     * 定义一个基于授权功能的注解类数组
     */
    private static final Class[] ANNOTATION_CLASS_ARRAY = {
            User.class
    };


    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        //从目标与目标方法中获取相应的注解
        Annotation annotation = getAnnotation(cls, method);
        if (annotation != null) {
            //判断授权注解的类型
            Class<?> annotationType = annotation.annotationType();
            if (annotationType.equals(User.class)) {
                handleUser();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Annotation getAnnotation(Class<?> cls, Method method) {
        //遍历所有的授权注解
        for (Class<? extends Annotation> annotationClass : ANNOTATION_CLASS_ARRAY) {
            //首先判断目标方法上是否带有授权注解
            if (method.isAnnotationPresent(annotationClass)){
                return method.getAnnotation(annotationClass);
            }
            //然后判断目标类上是否带有授权注解
            if (cls.isAnnotationPresent(annotationClass)) {
                return cls.getAnnotation(annotationClass);
            }
        }
        //若目标方法与目标类上均未带有授权注解，则返回空对象
        return null;
    }

    private void handleUser()  {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if (principals == null || principals.isEmpty()){
                throw new AuthzException("当前用户未登录");
            }
    }
}
