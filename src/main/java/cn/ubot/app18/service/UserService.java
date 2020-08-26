package cn.ubot.app18.service;

import cn.ubot.app18.common.EUComboBoxResult;
import cn.ubot.app18.common.EUDataGridResult;
import cn.ubot.app18.pojo.user.Permission;
import cn.ubot.app18.pojo.user.Role;
import cn.ubot.app18.pojo.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    /**
     * 根据用户名获取用户信息
     * @param name
     * @return
     */
    User getUserByName(String name);

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
    List<Permission> getPermissionsByRoleId(Long roleId);

    /**
     * 获取所有用户
     * @param page
     * @param rows
     * @return
     */
    EUDataGridResult getUsers(Integer page, Integer rows);

    /**
     * 获取所有权限
     * @param page
     * @param rows
     * @return
     */
    EUDataGridResult getPermissions(Integer page, Integer rows);

    /**
     * 获取所有角色
     * @param page
     * @param rows
     * @return
     */
    EUDataGridResult getRoles(Integer page, Integer rows);

    /**
     * 新增权限
     * @param permission
     * @return
     * @throws Exception
     */
    Integer addPermission(Permission permission) throws Exception;

    /**
     * 根据主键id删除权限
     * @param id
     * @return
     * @throws Exception
     */
    Integer deletePermissionById(Long id) throws Exception;

    /**
     * 新增角色
     * @param role
     * @param pids
     * @throws Exception
     */
    void addRole(Role role, List<Long> pids) throws Exception;

    /**
     * 修改角色
     * @param role
     * @param pids
     * @throws Exception
     */
    void updateRole(Role role, List<Long> pids) throws Exception;

    /**
     * 删除角色
     * @param id
     */
    void deleteRole(Long id);

    /**
     * 获取所有角色名
     * @return
     */
    List<EUComboBoxResult> getRoleNames();

    /**
     * 添加用户
     * @param name
     * @param password
     * @param rids
     */
    void addUser(String name,String password,List<Long> rids);

    /**
     * 删除用户
     * @param id
     * @return
     */
    void deleteUser(Long id);

    /**
     * 修改用户
     * @param user
     * @param rids
     */
    void updateUser(User user,List<Long> rids);

}
