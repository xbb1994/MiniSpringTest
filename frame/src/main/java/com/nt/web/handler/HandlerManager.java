package com.nt.web.handler;

import com.nt.web.mvc.Controller;
import com.nt.web.mvc.RequestMapping;
import com.nt.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/18 15:32
 * @description：管理器
 */
public class HandlerManager {

    //    静态属性，用来保存多个MappingHandler
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    //    筛选controller类，并获取其中的Mapping方法，初始化到mappinghandler
    public static void resolveMappingHandler(List<Class<?>> classList) {
        for (Class<?> clazz : classList) {
//            判断类是否有controller注解
            if (clazz.isAnnotationPresent(Controller.class)) {
//                解析controller类
                parasHandlerFromController(clazz);

            }
        }
    }


    /*
     * @Description 解析controller类
     * @Author xubb
     * @Date 2019/6/19
     * @Param [clazz]
     * @Return void
     */
    public static void parasHandlerFromController(Class<?> clazz) {
//        通过反射获取类的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
//            如果方法有requestMapping注解
            if (method.isAnnotationPresent(RequestMapping.class)) {
//                  获取方法的uri
                String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
                List<String> paramNameList = new ArrayList<>();
                for (Parameter parameter : method.getParameters()) {
//                    获取参数的名字
                    if (parameter.isAnnotationPresent(RequestParam.class)) {
                        paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                    }
                }

                String[] params = paramNameList.toArray(new String[paramNameList.size()]);
                MappingHandler mappingHandler = new MappingHandler(uri, method, clazz, params);
                mappingHandlerList.add(mappingHandler);

            }
        }

    }

}
