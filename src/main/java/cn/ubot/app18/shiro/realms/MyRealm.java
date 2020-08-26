package cn.ubot.app18.shiro.realms;

import cn.hutool.core.collection.CollUtil;
import cn.ubot.app18.pojo.user.Permission;
import cn.ubot.app18.pojo.user.Role;
import cn.ubot.app18.pojo.user.User;
import cn.ubot.app18.service.UserService;
import cn.ubot.app18.shiro.salt.MyByteSource;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * @author czw
 * @create 2020-07-31 14:18
 * 自定义Realm
 */

public class MyRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

//        // 获取用户名
//        String principal = (String) principalCollection.getPrimaryPrincipal();
//        // 根据用户名获取信息
//        User user = userService.getRolesByName(principal);
//        List<Role> roles = user.getRoles();
//        if(CollUtil.isEmpty(roles)){
//            return null;
//        }
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        // 添加用户角色
//        for (Role role : roles) {
//            simpleAuthorizationInfo.addRole(role.getName());
//            // 添加权限
//            List<Permission> permissions = userService.getPermissionsByRoleId(role.getId());
//            if(!CollUtil.isEmpty(permissions)){
//                for (Permission permission : permissions) {
//                    simpleAuthorizationInfo.addStringPermission(permission.getName());
//                }
//            }
//        }

        // 获取用户名
        String principal = (String) principalCollection.getPrimaryPrincipal();
        // 根据用户名获取信息
        List<Role> roles = userService.getRolesByName(principal);
        if(CollUtil.isEmpty(roles)){
            return null;
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 添加用户角色
        for (Role role : roles) {
            simpleAuthorizationInfo.addRole(role.getName());
            // 添加权限
            List<Permission> permissions = userService.getPermissionsByRoleId(role.getId());
            if(!CollUtil.isEmpty(permissions)){
                for (Permission permission : permissions) {
                    simpleAuthorizationInfo.addStringPermission(permission.getName());
                }
            }
        }

        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String principal = (String)authenticationToken.getPrincipal();

        User user = userService.getUserByName(principal);
        // 被锁定/未激活的用户
        if (user.getStatus().intValue() == 0) {
            throw new LockedAccountException();
        }

        String name = user.getName();
        String password = user.getPassword();
        String salt = user.getSalt();

        if(name.equals(principal)){
            /**
             * 参数1：token中传来的用户名
             * 参数2：数据库中获取的密码
             * 参数3：盐
             * 参数4：当前域的名称
             */
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, password, new MyByteSource(salt), this.getName());
            return simpleAuthenticationInfo;
        }

        return null;
    }




}
