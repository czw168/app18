package cn.ubot.app18.controller;

import cn.ubot.app18.common.AjaxResult;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 页面跳转
 * @author czw
 * @create 2020-08-17 15:54
 */
@Controller
public class PageController {

    /**
     * 登录页面
     * @return
     */
    @GetMapping("/")
    public String login(){
        return "login";
    }

    /**
     * 首页
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 无访问权限时返回的页面
     * @return
     */
    @GetMapping("/unauthorized")
    public Object unauthorized() {
        return "unauthorized";
    }

    /**
     * 功能模块-用户管理
     * @param page
     * @return
     */
    @GetMapping("/functionModule/user/{page}")
    @RequiresRoles("admin")
    public String functionModelUser(@PathVariable("page") String page){
        return "/functionModule/user/" + page;
    }

    /**
     * 产品跟踪-条码扫描
     * @param page
     * @return
     */
    @GetMapping("/productTracking/scan/{page}")
    @RequiresRoles(value={"admin","product"},logical = Logical.OR)
    public String productTrackingScan(@PathVariable("page") String page){
        return "/productTracking/scan/" + page;
    }

}
