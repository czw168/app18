package cn.ubot.app18.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.ubot.app18.common.EUComboBoxResult;
import cn.ubot.app18.common.EUDataGridResult;
import cn.ubot.app18.mapper.user.UserMapper;
import cn.ubot.app18.pojo.user.Permission;
import cn.ubot.app18.pojo.user.Role;
import cn.ubot.app18.pojo.user.User;
import cn.ubot.app18.utils.SaltUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public List<Role> getRolesByName(String name) {
        return userMapper.getRolesByName(name);
    }

    @Override
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        return userMapper.getPermissionsByRoleId(roleId);
    }

    @Override
    public EUDataGridResult getUsers(Integer page, Integer rows) {
        EUDataGridResult result = new EUDataGridResult();
        PageHelper.startPage(page.intValue(), rows.intValue());
        List<User> users = userMapper.getUsers();
        for(User user : users){
            // 根据用户名获取角色列表
            List<Role> roles = userMapper.getRolesByName(user.getName());
            user.setRoles(roles);
        }
        result.setRows(users);
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public EUDataGridResult getPermissions(Integer page, Integer rows) {
        EUDataGridResult result = new EUDataGridResult();
        if(!StringUtils.isEmpty(page) && !StringUtils.isEmpty(rows)){
            PageHelper.startPage(page.intValue(), rows.intValue());
        }
        List<Permission> permissions = userMapper.getPermissions();
        result.setRows(permissions);
        PageInfo<Permission> pageInfo = new PageInfo<Permission>(permissions);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public EUDataGridResult getRoles(Integer page, Integer rows) {
        EUDataGridResult result = new EUDataGridResult();
        PageHelper.startPage(page.intValue(), rows.intValue());
        List<Role> roles = userMapper.getRoles();
        for(Role role : roles){
            // 根据角色ID获取权限信息
            List<Permission> permissions = userMapper.getPermissionsByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        result.setRows(roles);
        PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addPermission(Permission permission) throws Exception{
        return userMapper.addPermission(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deletePermissionById(Long id) throws Exception{
        return userMapper.deletePermissionById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(Role role, List<Long> pids) throws Exception{
        userMapper.addRole(role);
        if(!CollUtil.isEmpty(pids)){
            long rid = role.getId();
            userMapper.addRolePermission(rid,pids);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Role role, List<Long> pids) throws Exception {
        userMapper.updateRole(role);
        userMapper.deletePermissionByRid(role.getId());
        userMapper.addRolePermission(role.getId(),pids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        userMapper.deleteRole(id);
        userMapper.deletePermissionByRid(id);
    }

    @Override
    public List<EUComboBoxResult> getRoleNames() {
        List<EUComboBoxResult> result = new ArrayList<EUComboBoxResult>();
        List<Role> roles = userMapper.getRoles();
        for (Role role : roles) {
            EUComboBoxResult euResult = new EUComboBoxResult(role.getId() + "", role.getName(), null);
            result.add(euResult);
        }
        return result;
    }

    @Override
    public void addUser(String name, String password, List<Long> rids) {
        User user = new User();
        // 生成随机盐
        String salt = SaltUtils.getSalt(8);
        // 明文密码进行md5 + salt + hash散列
        Md5Hash md5Hash = new Md5Hash(password,salt,2);
        user.setCreateTime(DateUtil.date());
        user.setName(name);
        user.setPassword(md5Hash.toHex());
        user.setSalt(salt);
        user.setStatus(1);
        userMapper.addUser(user);
        userMapper.addUserRole(user.getId(),rids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
        userMapper.deleteUserRole(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user, List<Long> rids) {
        if(!StringUtils.isEmpty(user.getPassword())){
            // 生成随机盐
            String salt = SaltUtils.getSalt(8);
            // 明文密码进行md5 + salt + hash散列
            Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,2);
            user.setPassword(md5Hash.toHex());
            user.setSalt(salt);
        }
        user.setUpdateTime(DateUtil.date());
        userMapper.updateUser(user);
        // 修改权限实际就是先删除现有的权限，再新增
        userMapper.deleteUserRole(user.getId());
        userMapper.addUserRole(user.getId(),rids);
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.date());
    }

}
