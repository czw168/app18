package cn.ubot.app18.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ubot.app18.common.AjaxResult;
import cn.ubot.app18.common.EUComboBoxResult;
import cn.ubot.app18.common.EUDataGridResult;
import cn.ubot.app18.pojo.user.Permission;
import cn.ubot.app18.pojo.user.Role;
import cn.ubot.app18.pojo.user.User;
import cn.ubot.app18.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户操作相关
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param obj
     * @return
     */
    @PostMapping("/loginto")
    @ResponseBody
    public AjaxResult loginto(@RequestBody JSONObject obj){

        String name = obj.getStr("name");
        String password = obj.getStr("password");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);

        try {
            subject.login(token);
            return AjaxResult.ok();
        } catch (LockedAccountException lae) {
            return AjaxResult.build(400, "用户已被锁定！");
        } catch (AuthenticationException e) {
            return AjaxResult.build(400, "用户名或密码错误！");
        } catch (Exception e){
            e.printStackTrace();
            return AjaxResult.build(500, "Error！");
        }

    }

    /**
     * 注销
     * @return
     */
    @PostMapping("/loginOut")
    @ResponseBody
    public AjaxResult loginOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return AjaxResult.ok();

    }

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("users")
    @ResponseBody
    @RequiresRoles("admin")
    public EUDataGridResult users(Integer page, Integer rows){
        return userService.getUsers(page,rows);
    }

    /**
     * 获取所有权限
     * @return
     */
    @GetMapping("permissions")
    @ResponseBody
    @RequiresRoles("admin")
    public EUDataGridResult permissions(@RequestParam(required = false) Integer page, @RequestParam(required = false)Integer rows){
        return userService.getPermissions(page,rows);
    }

    /**
     * 获取所有角色
     * @return
     */
    @GetMapping("roles")
    @ResponseBody
    @RequiresRoles("admin")
    public EUDataGridResult roles(Integer page, Integer rows){
        return userService.getRoles(page,rows);
    }

    @PostMapping("permissions")
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxResult addPermission(@RequestBody JSONObject obj){
        Permission permission = JSONUtil.toBean(obj, Permission.class);
        try {
            userService.addPermission(permission);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.build(500,e.getMessage());
        }
        return AjaxResult.ok();
    }

    @DeleteMapping("permissions")
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxResult deletePermission(@RequestBody JSONObject obj){
        String id = obj.getStr("id");
        try {
            userService.deletePermissionById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.build(500,e.getMessage());
        }
        return AjaxResult.ok();
    }

    @PostMapping("roles")
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxResult addRole(@RequestBody JSONObject obj){
        String name = obj.getStr("name");
        String desc = obj.getStr("desc");
        Role role = new Role();
        role.setName(name);
        role.setDesc(desc);
        List<Long> pids = obj.getJSONArray("pids").toList(Long.class);
        try {
            userService.addRole(role, pids);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.build(500,e.getMessage());
        }
        return AjaxResult.ok();
    }

    @PutMapping("roles")
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxResult updateRole(@RequestBody JSONObject obj){
        Long id = obj.getLong("id");
        String desc = obj.getStr("desc");
        Role role = new Role();
        role.setId(id);
        role.setDesc(desc);
        List<Long> pids = obj.getJSONArray("pids").toList(Long.class);
        try {
            userService.updateRole(role,pids);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.build(500,e.getMessage());
        }
        return AjaxResult.ok();
    }

    @DeleteMapping("roles")
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxResult deleteRole(@RequestBody JSONObject obj){
        Long id = obj.getLong("id");
        try {
            userService.deleteRole(id);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.build(500,e.getMessage());
        }
        return AjaxResult.ok();
    }

    @GetMapping("/{name}")
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxResult getUserByName(@PathVariable("name") String name){
        User user = userService.getUserByName(name);
        return AjaxResult.ok(user);
    }

    @GetMapping("roleNames")
    @ResponseBody
    @RequiresRoles("admin")
    public List<EUComboBoxResult> roleNames(){
        return userService.getRoleNames();
    }

    @PostMapping("users")
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxResult addUser(@RequestBody JSONObject obj){
        String name = obj.getStr("name");
        String password = obj.getStr("password");
        List<Long> rids = obj.getJSONArray("rids").toList(Long.class);
        try {
            userService.addUser(name,password,rids);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.build(500,e.getMessage());
        }
        return AjaxResult.ok();
    }

    @DeleteMapping("users")
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxResult deleteUser(@RequestBody JSONObject obj){
        Long id = obj.getLong("id");
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.build(500,e.getMessage());
        }
        return AjaxResult.ok();
    }

    @PutMapping("users")
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxResult updateUser(@RequestBody JSONObject obj){
        User user = obj.getBean("user", User.class);
        List<Long> rids = obj.getJSONArray("rids").toList(Long.class);
        try {
            userService.updateUser(user,rids);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.build(500,e.getMessage());
        }
        return AjaxResult.ok();
    }

}
