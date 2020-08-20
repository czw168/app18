package cn.ubot.app18.controller;

import cn.ubot.app18.common.AjaxResult;
import cn.ubot.app18.pojo.user.User;
import cn.ubot.app18.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
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

        String name = obj.getString("name");
        String password = obj.getString("password");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);

        try {
            subject.login(token);
            return AjaxResult.ok();
        } catch (LockedAccountException lae) {
            return AjaxResult.build(400, "用户被锁定！");
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


//    @RequestMapping("/register")
//    public String register(User user){
//        try {
//            userService.register(user);
//            return "redirect:/";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "redirect:/register";
//    }

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("getUsers")
    @ResponseBody
    public List<User> getUsers(){
        List<User> users = userService.getUsers();
        return users;
    }

}
