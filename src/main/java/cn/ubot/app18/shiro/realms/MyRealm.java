package cn.ubot.app18.shiro.realms;

import cn.ubot.app18.pojo.user.Permission;
import cn.ubot.app18.pojo.user.Role;
import cn.ubot.app18.pojo.user.User;
import cn.ubot.app18.service.UserService;
import cn.ubot.app18.shiro.salt.MyByteSource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;

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
        //UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 获取用户名
        String principal = (String) principalCollection.getPrimaryPrincipal();
        // 根据用户名获取角色信息
        Role role = userService.getRole(principal);
        if(null == role){
            return simpleAuthorizationInfo;
        }

        // 添加用户角色
        simpleAuthorizationInfo.addRole(role.getName());

        // 根据角色ID获取权限
        List<Permission> permissions = userService.getPermissions(role.getId());

        if(!CollectionUtils.isEmpty(permissions)){
            for (Permission permission : permissions) {
                String name = permission.getName();
                // 添加用户权限
                simpleAuthorizationInfo.addStringPermission(name);
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

        //UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        User user = userService.getUser(principal);
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
            //SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, password, ByteSource.Util.bytes(salt), this.getName());
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, password, new MyByteSource(salt), this.getName());
            return simpleAuthenticationInfo;
        }

        return null;
    }




}
