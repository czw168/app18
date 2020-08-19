package cn.ubot.app18.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author czw
 * @create 2020-08-17 15:54
 */
@Controller
public class PageController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
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
     * 产品跟踪-条码扫描
     * @param page
     * @return
     */
    @GetMapping("/productTracking/scan/{page}")
    public String productTrackingScan(@PathVariable("page") String page){
        return "/productTracking/scan/" + page;
    }

}
