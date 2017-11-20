package com.zsx.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

/**
 * @author: Ian
 * @Time: 2017/11/20.16:27
 * @Description:
 */
public class AppContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    public void contextInitialized(ServletContextEvent event) {
        try {

            ServletContext sc = event.getServletContext();
            String appName = sc.getServletContextName();
            logger.info("[" + appName + "] init context ...");

            setVersionAndCDN(sc);
            logger.info("set version and other info end");

        } catch (Exception e) {
            logger.error("error detail:", e);
        }
    }

    private void setVersionAndCDN(ServletContext sc){
        Properties props;
        String cdnPath=null ;
        String version =  System.currentTimeMillis() + "";//props.getProperty("frontend.file.version");
        try {
            props = PropertiesLoaderUtils
                    .loadAllProperties("application.properties");
//            version =  System.currentTimeMillis() + "";//props.getProperty("frontend.file.version");
            cdnPath = props.getProperty("frontend.cdn.path");

        } catch (Exception e) {

            logger.info("get frontend version and cdnPath fail");
        }
        if (cdnPath == null) {
            cdnPath = sc.getContextPath();
        }
        sc.setAttribute("cdnPath", cdnPath);
        sc.setAttribute("ver", version);
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        logger.info("Invoke ApplicationContextListener contextDestroyed");
    }

}
