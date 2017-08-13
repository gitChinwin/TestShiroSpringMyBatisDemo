package cn.chinwin.demo.utils;


import cn.chinwin.demo.dao.impl.PrivilegeDaoImpl;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * Created by chinwin on 2017/8/10.
 */
public class WebApplicationListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        PrivilegeDaoImpl privilegeDao = applicationContext.getBean(PrivilegeDaoImpl.class);
        List<String> urlList = privilegeDao.findAllUrl();
        sce.getServletContext().setAttribute("urlList",urlList);
        System.out.println("初始化成功！");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
