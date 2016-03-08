package groovy.com.geo.service

import groovy.com.geo.dao.TestUserDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by GeorgeZeng on 16/3/2.
 */
@Service
@Transactional
@Import([groovy.com.geo.aop.TestAspect.class, groovy.com.geo.config.TestResourceConfig.class])
class TestUserService {
    @Autowired
    TestUserDAO dao

    def list() {
        dao.list()
    }

    def save(user) {
        dao.save(user)
    }
}