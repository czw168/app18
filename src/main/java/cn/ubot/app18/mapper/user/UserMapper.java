package cn.ubot.app18.mapper.user;

import cn.ubot.app18.pojo.user.Permission;
import cn.ubot.app18.pojo.user.Role;
import cn.ubot.app18.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserMapper {

    /**
     * 根据用户名获取用户信息
     * @param name
     * @return
     */
    User getUserByName(@Param("name") String name);

    /**
     * 获取当前用户下的所有角色
     * @param name
     * @return
     */
    List<Role> getRolesByName(@Param("name") String name);

    /**
     * 根据角色ID获取权限
     * @param roleId
     * @return
     */
    List<Permission> getPermissionsByRoleId(@Param("roleId") Long roleId);

    /**
     * 获取所有用户信息
     * @return
     */
    List<User> getUsers();

    /**
     * 获取所有权限
     * @return
     */
    List<Permission> getPermissions();

    /**
     * 获取所有角色
     * @return
     */
    List<Role> getRoles();

    /**
     * 新增权限
     * @param permission
     */
    Integer addPermission(Permission permission);

    /**
     * 根据主键id删除权限
     * @param id
     */
    Integer deletePermissionById(Long id);

    /**
     * 新增角色
     * @param role
     * @return
     */
    Integer addRole(Role role);

    /**
     * 给角色分配权限
     * @param rid
     * @param pids
     * @return
     */
    Integer addRolePermission(Long rid, List<Long> pids);

    /**
     * 修改角色
     * @param role
     * @return
     */
    Integer updateRole(Role role);

    /**
     * 根据角色Id删除权限
     * @param rid
     * @return
     */
    Integer deletePermissionByRid(Long rid);

    /**
     * 删除角色
     * @param id
     * @return
     */
    Integer deleteRole(Long id);

    /**
     * 新增用户
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 给用户添加角色
     * @param uid
     * @param rids
     * @return
     */
    Integer addUserRole(Long uid, List<Long> rids);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Integer deleteUser(Long id);

    /**
     * 删除用户角色
     * @param uid
     * @return
     */
    Integer deleteUserRole(Long uid);

    /**
     * 修改用户
     * @param user
     * @return
     */
    Integer updateUser(User user);





}
