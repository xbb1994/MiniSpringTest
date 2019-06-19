package com.nt.web.handler;

import com.nt.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/18 15:29
 * @description：请求映射器？
 */
public class MappingHandler {
    private String uri;
    //对应的controller方法
    private Method method;
    //    对应的类
    private Class<?> controller;
    //    对应方法所需要的参数
    private String[] args;


    public MappingHandler(String uri, Method method, Class<?> clazz, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = clazz;
        this.args = args;
    }


    /*  
     * @Description 处理请求
     * @Author xubb
     * @Date 2019/6/19 
     * @Param [req, res]  
     * @Return boolean  
     */ 
    public boolean handle(ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {

        String reqUri = ((HttpServletRequest) req).getRequestURI();
//        判断handler里面的url跟请求的url是否相同
        if (!uri.equals(reqUri)) {
            return false;
        }
        //获取参数
        Object[] params = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            params[i] = req.getParameter(args[i]);

        }
//        Object ctl = controller.newInstance();
//        这边换成从beanfactory中获取
        Object ctl = BeanFactory.getBean(controller);
        //不确定类型，用Object存储
        Object response = method.invoke(ctl, params);
        // 将方法返回的接口放到servletresponse里面去
        res.getWriter().println(response.toString());
        return true;
    }


}
