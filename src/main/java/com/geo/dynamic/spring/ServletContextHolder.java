package com.geo.dynamic.spring;

import javax.servlet.ServletContext;

/**
 * Created by GeorgeZeng on 16/2/26.
 */
public interface ServletContextHolder {
    ThreadLocal<ServletContext> CONTEXT = new ThreadLocal<ServletContext>();
}
