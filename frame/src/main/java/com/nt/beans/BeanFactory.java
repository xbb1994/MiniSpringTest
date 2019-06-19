package com.nt.beans;

import com.nt.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/18 17:41
 * @description：用来初始化并保存bean
 */
public class BeanFactory {
    //    存储bean类型到bean实例的映射
    //    用concurrentHashMap，预留后续拓展做并发处理
    private static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();


    /*
     * @Description 从映射里获取bean
     * @Author xubb
     * @Date 2019/6/19
     * @Param [clazz]
     * @Return java.lang.Object
     */
    public static Object getBean(Class<?> clazz) {
        return classToBean.get(clazz);
    }

    /*
     * @Description 初始化
     * @Author xubb
     * @Date 2019/6/19
     * @Param [classList]  传入之前的类定义
     * @Return void
     */
    public static void initBean(List<Class<?>> classList) throws Exception {
//       传入进来的类后续还会使用，所以创建新的容器用以区分
        List<Class<?>> toCreate = new ArrayList<>(classList);
//        当容器内中还有类定义时，就需要继续遍历，初始化完的，需要从容器中删掉
        while (toCreate.size() != 0) {
            int originSize = toCreate.size();
            for (int i = 0; i < toCreate.size(); i++) {
//                如果完成遍历，需要从容器中删除
                if (finishCreate(toCreate.get(i))) {
                    toCreate.remove(i);
                }
            }
//          如果容器大小没有变化，则可能进入了死循环或者出了问题，需要抛出异常！
            if (toCreate.size() == originSize) {
                throw new Exception("Init Bean With Exception!");
            }

        }
    }

    private static boolean finishCreate(Class<?> clazz) throws IllegalAccessException, InstantiationException {
//        需要判断是否需要初始化为bean
        if(!clazz.isAnnotationPresent(Bean.class)&&!clazz.isAnnotationPresent(Controller.class)){
//            不需要直接返回true，在上面的循环中删除
            return true;
        }

//        初始化
        Object bean = clazz.newInstance();
//        初始化完成需要看类的属性是否有需要依赖注入的
        for (Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(AutoWired.class)){
                Class<?> fieldType = field.getType();
//              根据拿到的类型，去beanfactory中获取bean
                Object object = BeanFactory.getBean(fieldType);
//                如果不存在，那肯定会创建失败，直接返回false
                if (object == null) {
                    return false;
                }
//                如果存在，那就注入到初始化的bean中

                field.setAccessible(true);
                field.set(bean,object);


            }
        }
        classToBean.put(clazz,bean);
        return  true;
    }


}
