package com.nt.beans;

import java.lang.annotation.*;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/18 17:37
 * @description：表示这个类可以解析为bean
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Bean {
}
