package com.geo.service

import com.geo.dao.TestUserDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Service

/**
 * Created by GeorgeZeng on 16/3/2.
 */
@Service
@Import([com.geo.aop.TestAspect.class, com.geo.config.TestResourceConfig.class])
class TestService {
    @Autowired
    TestUserDAO dao

    def list() {
        dao.list()
    }

    def save(user) {
        dao.save(user)
    }
}