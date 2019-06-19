package com.nt.web.mvc;

import java.lang.annotation.*;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/14 22:05
 * @description：
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
    String value();
}
