package cn.chinwin.demo.utils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录验证拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {


    private static String[] urls = {"uc/checklogin"};//不过滤的url地址，也可在配置文件中配置

    /**
     * 判断当前请求地址 是否 属于不过滤的地址集合
     * 如果是静态资源。则true
     * @param requestName
     * @return
     */
    private static boolean checkUrl(String requestName) {
        //请求地址和当前地址相同
        if (requestName == null || requestName.equals("")) {
            return true;
        }
        //静态资源免拦截
        if (requestName.endsWith(".js") || requestName.endsWith(".css") || requestName.endsWith(".jpg")) {
            return true;
        }
        //匹配
        for (String url : urls) {
            if (url.equals(requestName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("///////......");
        //获取当前请求的名字,是这种格式滴----    /xxx/xxx    -----
        RequestNameUtil nameUtil = new RequestNameUtil(request);
        String requestName = nameUtil.getRequestName();
        String basePath = nameUtil.getBasePath();
        HttpSession session = request.getSession();
        System.out.println(requestName);
        if (checkUrl(requestName)) {
            return true;
        }
        Object user = session.getAttribute("users");
        if (user == null) {
            response.sendRedirect(basePath + "sign-in.html");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
