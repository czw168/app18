package cn.ubot.app18.service;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author czw
 * @create 2020-08-19 9:42
 */
@Aspect
@Service
@Slf4j
public class AOPServcieImpl implements AOPServcie{

    @Resource
    private HttpServletRequest request;

    @Pointcut("execution(public * cn.ubot.app18.controller.*.*(..)))")
    public void brokerAspect(){

    }

    @Override
    @After("brokerAspect()")
    public void addlog() {
        log.info("======================== 当前时间【" + DateUtil.date() + "】\t请求路径【" + request.getServletPath() + "】");
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.date());
    }

}
