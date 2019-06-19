package com.nt.starter;

import com.nt.beans.BeanFactory;
import com.nt.core.ClassScanner;
import com.nt.web.handler.HandlerManager;
import com.nt.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/13 19:17
 * @description：框架启动类
 */
public class MiniApplication {

    public static void run(Class clazz, String[] args) {
        System.out.println("This is MiniApplication");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
//            扫描所有的类
            List<Class<?>> classList = ClassScanner.scanClass(clazz.getPackage().getName());
//            初始化bean
            BeanFactory.initBean(classList);
//          从所有的类中筛选出controller，  初始化所有的mappinghandler
            HandlerManager.resolveMappingHandler(classList);
            for (int i = 0; i < classList.size(); i++) {
                System.out.println(classList.get(i).getName());
            }

        } catch (LifecycleException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
