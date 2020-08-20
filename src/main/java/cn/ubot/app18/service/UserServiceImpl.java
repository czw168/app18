package cn.ubot.app18.service;


import cn.ubot.app18.mapper.user.UserMapper;
import cn.ubot.app18.pojo.user.Permission;
import cn.ubot.app18.pojo.user.Role;
import cn.ubot.app18.pojo.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public User getUser(String name) {
        return userMapper.getUser(name);
    }

    @Override
    public Role getRole(String name) {
        return userMapper.getRole(name);
    }

    @Override
    public List<Permission> getPermissions(Long roleId) {
        return userMapper.getPermissions(roleId);
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void register(User user) {
//        // 生成盐
//        String salt = SaltUtils.getSalt(8);
//        user.setSalt(salt);
//        // 明文密码 + MD5 + salt + hash散列
//        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 2);
//        user.setPassword(md5Hash.toHex());
//        // 设置默认状态
//        user.setStatus(1);
//        // 当前日期
//        user.setCreateTime(new Date(System.currentTimeMillis()));
//        userMapper.register(user);
//        int i = 1/0;
//        userMapper.register(user);
//    }


}
