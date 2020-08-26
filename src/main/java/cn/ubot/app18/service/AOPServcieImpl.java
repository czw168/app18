package cn.ubot.app18.service;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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

    @Pointcut("execution(public * cn.ubot.app18.service..*.*(..))")
    public void brokerAspect(){

    }

    @Override
    @Before("brokerAspect()")
    public void addlog() {
        log.info("==> 用户【" + SecurityUtils.getSubject().getPrincipal() + "】\t时间【" + DateUtil.date() + "】\tIP【" + request.getRemoteAddr() + "】\tURL【" + request.getServletPath() + "】");
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.date());
    }

}
