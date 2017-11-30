package cn.univyz.plugin.security.tag;

import org.apache.shiro.web.tags.PermissionTag;

public class HasAllPermissions extends PermissionTag{
    protected boolean showTagBody(String s) {
        return false;
    }
}
