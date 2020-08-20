package cn.ubot.app18.exception;

import cn.hutool.json.JSONUtil;
import cn.ubot.app18.common.AjaxResult;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro异常相关处理
 * @author czw
 * @create 2020-08-20 15:25
 */
@ControllerAdvice
public class ShiroException {

    /**
     * 未经授权的异常
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, HttpServletResponse response,UnauthorizedException e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("ex", e);
        mv.setViewName("unauthorized");
        return mv;
    }


//    @ExceptionHandler({ UnauthorizedException.class})
//    public Object authorizationException(HttpServletRequest request, HttpServletResponse response, UnauthorizedException e) {
//        if (isAjaxRequest(request)) {
//            // 输出JSON
//            Map<String,Object> map = new HashMap<>();
//            map.put("code", "-998");
//            map.put("message", e);
//            writeJson(map, response);
//            return null;
//        } else {
//            ModelAndView mv = new ModelAndView();
//            mv.addObject("ex", e);
//            mv.setViewName("unauthorized");
//            return mv;
//        }
//    }
//
//
//    /**
//     * 输出JSON
//     *
//     * @param response
//     * @author SHANHY
//     * @create 2017年4月4日
//     */
//    private void writeJson(Map<String,Object> map, HttpServletResponse response) {
//        PrintWriter out = null;
//        try {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            out = response.getWriter();
//            out.write(JSONUtil.toJsonStr(map));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//        }
//    }
//
//
//    /**
//     * 是否是Ajax请求
//     *
//     * @param request
//     * @return
//     * @author SHANHY
//     * @create 2017年4月4日
//     */
//    public static boolean isAjaxRequest(HttpServletRequest request) {
//        String requestedWith = request.getHeader("x-requested-with");
//        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
//            return true;
//        } else {
//            return false;
//        }
//    }


}
