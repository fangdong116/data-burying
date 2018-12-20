package com.qingyang.test.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlHint {
    /**
     * 是否走从库
     * @return
     */
    boolean routeMaster() default true;
}
