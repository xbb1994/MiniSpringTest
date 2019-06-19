package com.nt.beans;

import java.lang.annotation.*;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/18 17:39
 * @description：使用在bean的属性上，被他注解的属性，需要添加对应的依赖
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoWired {
}
