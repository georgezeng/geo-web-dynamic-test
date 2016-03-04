package com.geo.dynamic.spring.annotation;

import java.lang.annotation.*;

/**
 * Created by GeorgeZeng on 16/2/21.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithConfig {
    Class<?>[] classes();
}
