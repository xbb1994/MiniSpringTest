package com.nt;

import com.nt.starter.MiniApplication;

/**
 * @description: 启动类
 * @author: Xu
 * @create: 2019-06-12 19:57
 **/
public class Application {
    public static void main(String[] args) {
        System.out.println("Hello World");
        MiniApplication.run(Application.class,args);
    }


}
