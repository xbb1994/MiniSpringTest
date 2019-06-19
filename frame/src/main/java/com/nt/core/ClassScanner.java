package com.nt.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/14 22:20
 * @description：
 */
public class ClassScanner {
    public static List<Class<?>> scanClass(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        //把包名替换成路径
        String packagePath = packageName.replace(".", "/");
        //获取默认的类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //获取可遍历的url资源--注意这边你参数为path，不是name
        Enumeration<URL> resources = classLoader.getResources(packagePath);
        //进行遍历
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            //由于项目最终都是会打成jar包，所以这边先处理资源类型为jar的情况
            if (resource.getProtocol().contains("jar")) {
                //先获取jar包的绝对路径
                //通过资源打开的连接，需要强转成jar包连接
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();

                //通过jar包路径获取jar包下所有的类
                classList.addAll(getClassesByJar(jarFilePath, packagePath));
            }

            //to do 其他资源类型
        }
        return classList;

    }



    /*
     * @Description 通过jar包路径获取jar包下所有的类
     * @Author xubb
     * @Date 2019/6/17
     * @Param [jarFilePath, path]
     * jar包路径
     * 里面有很多类文件，指定哪些文件是我么需要的
     * @Return java.util.List<java.lang.Class<?>>
     */
    private static List<Class<?>> getClassesByJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();//  com/nt/test/Test.class
            //以我们指定路径为开头，并以class为结尾的文件，
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                //jarEntry对应的类
                String classFullName = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                //通过类加载器加载
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;


    }


}
