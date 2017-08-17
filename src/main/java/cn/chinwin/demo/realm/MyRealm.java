package cn.chinwin.demo.realm;


import cn.chinwin.demo.pojo.Users;
import cn.chinwin.demo.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {

    @Resource(name = "userServiceImpl")
    private IUserService service;

    //授权，将当前用户的的权限集合设置进shiro提供的容器中
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        String username = (String) pc.fromRealm(getName()).iterator().next();
        if (username != null && "".equals(username)) {
            List<String> permissions = service.getPermissionsByUserName(username);
            if (permissions != null && !permissions.isEmpty()) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                info.addStringPermissions(permissions);
                return info;
            }
        }
        return null;
    }

    //登录验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) at;
        String username = token.getUsername();
        if (username != null && "".equals(username)) {
            Users user = service.islogin(new Users(username));
            if (user != null) {
                return new SimpleAuthenticationInfo(user.getLoginName(), user.getLoginPwd(), getName());
            }
        }
        return null;
    }
}
