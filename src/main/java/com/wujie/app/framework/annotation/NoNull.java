package com.wujie.app.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: wy
 * @date: 2019/3/8
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
public @interface NoNull {
    String[] fieldNames();
}
