package com.geo.config

/**
 * Created by GeorgeZeng on 16/3/5.
 */
import java.lang.annotation.*;

/**
 * Created by GeorgeZeng on 16/2/21.
 */
@Target([ElementType.TYPE ])
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface WithConfig {
    Class<?>[] classes();
}