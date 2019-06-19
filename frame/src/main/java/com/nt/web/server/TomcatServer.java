package com.nt.web.server;

import com.nt.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/13 22:29
 * @description：
 */
public class TomcatServer {

    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args) {
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(6699);
        tomcat.start();
        //建立tomcat跟servlet的关系
        //new 一个标准的context
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        DispatcherServlet servlet = new DispatcherServlet();
        //将servlet添加进context容器，取名testServlet,支持异步
        Tomcat.addServlet(context,"dispatcherServlet",servlet).setAsyncSupported(true);
        //添加Tomcat到uri的映射，访问uri，才会调用相应的servlet
        context.addServletMappingDecoded("/","dispatcherServlet");
        //context需要依赖在host容器
        tomcat.getHost().addChild(context);

        //防止中途退出，添加长路线程
        Thread awaitThread = new Thread("tomcat_wait_thread"){
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        //设置为非守护线程
        awaitThread.setDaemon(false);
        //一直在等待
        awaitThread.start();
    }
}
