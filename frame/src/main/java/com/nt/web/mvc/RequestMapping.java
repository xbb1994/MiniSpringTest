package com.nt.web.mvc;

import java.lang.annotation.*;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/14 22:04
 * @description：
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RequestMapping {
    String value();
}
