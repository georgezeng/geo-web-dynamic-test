package com.geo.config

import org.springframework.context.annotation.Configuration

/**
 * Created by GeorgeZeng on 16/3/3.
 */
@Configuration
class TestResourceConfig extends BasicResourceConfig {

    @Override
    protected Class<?>[] defineAnnotatedClasses() {
        return [com.geo.entity.TestUser.class]
    }

}
