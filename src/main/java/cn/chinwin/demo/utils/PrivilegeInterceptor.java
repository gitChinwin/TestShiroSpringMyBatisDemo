package cn.chinwin.demo.utils;

import cn.chinwin.demo.pojo.Privilege;
import cn.chinwin.demo.pojo.Users;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 权限认证拦截器，在登录拦截器之后，因此不需判断用户是否登录！
 */
public class PrivilegeInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        ServletContext application = session.getServletContext();
        List<String> urlList = (List<String>) application.getAttribute("urlList");
        RequestNameUtil nameUtil = new RequestNameUtil(request);
        String requestName = nameUtil.getRequestName();
        String basePath = nameUtil.getBasePath();
        if (!splitCheck(urlList, requestName)) {
            return true;
        }
        Users user = (Users) session.getAttribute("users");
        if (user.getRole() == null) {
            response.sendRedirect(basePath + "noPrivilege.jsp");
            return false;
        }
        List<Privilege> priList = user.getRole().getPriList();
        if (!checkPri(requestName, priList)) {
            response.sendRedirect(basePath + "noPrivilege.jsp");
            return false;
        }
        return true;
    }


    public static boolean checkPri(String requestName, List<Privilege> priList) {
        if (priList == null && priList.isEmpty()) {
            return false;
        }
        //开始遍历比对
        for (Privilege privilege : priList) {
            String priUrls = privilege.getPriUrl();
            if(priUrls==null){
                continue;
            }
            String[] urls = priUrls.split(";");
            for (int i = 0; i < urls.length; i++) {
                if (requestName.equals(urls[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean splitCheck(List<String> priUrls, String requestName) {
        for (int i = 0; i < priUrls.size(); i++) {
            String priUrl = priUrls.get(i);
            if(priUrl==null){
                continue;
            }
            String[] urls = priUrl.split(";");
            for (int j = 0; j < urls.length; j++) {
                if (requestName.equals(urls[j])) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
