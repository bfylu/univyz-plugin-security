package cn.univyz.plugin.security.tag;

import org.apache.shiro.web.tags.PermissionTag;

public class HasAnyPermissions extends PermissionTag{
    protected boolean showTagBody(String s) {
        return false;
    }
}
