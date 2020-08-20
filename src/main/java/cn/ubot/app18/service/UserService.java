package cn.ubot.app18.service;

import cn.ubot.app18.pojo.user.Permission;
import cn.ubot.app18.pojo.user.Role;
import cn.ubot.app18.pojo.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    /**
     * 获取所有用户
     * @return
     */
    List<User> getUsers();

    /**
     * 根据用户名获取用户信息
     * @param name
     * @return
     */
    User getUser(@Param("name") String name);

    /**
     * 根据用户名获取角色ID
     * @param name
     * @return
     */
    Role getRole(@Param("name") String name);

    /**
     * 根据角色ID获取权限
     * @param roleId
     * @return
     */
    List<Permission> getPermissions(Long roleId);

    /**
     * 注册（添加用户）
     * @param user
     */
    //void register(User user);

}
