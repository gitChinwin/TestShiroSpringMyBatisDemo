package cn.chinwin.demo.utils;

import sun.net.idn.Punycode;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chinwin on 2017/8/10.
 */
public class RequestNameUtil {

    private String requestName;
    private String basePath;


    public RequestNameUtil(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        requestName = uri.substring(contextPath.length() + 1, uri.length());
        basePath = request.getScheme() + "://" + request.getServerName() + ":" +
                request.getServerPort() + "/";
    }

    protected RequestNameUtil() {

    }

    public String getRequestName() {
        return requestName;
    }

    protected void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getBasePath() {
        return basePath;
    }

    protected void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
