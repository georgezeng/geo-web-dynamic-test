package com.geo.config

import org.springframework.context.annotation.Configuration

/**
 * Created by GeorgeZeng on 16/3/3.
 */
@Configuration
class TestResourceConfig extends BasicResourceConfig {

    @Override
    protected Class<?>[] defineHibernateAnnotatedClasses() {
        return [com.geo.entity.TestUser.class]
    }

    @Override
    protected Properties defineHibernateProperties() {
        def props = new Properties()
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
        props.setProperty("hibernate.show_sql", "true")
        props.setProperty("hibernate.format_sql", "true")
        props
    }

}
