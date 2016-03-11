package groovy.com.geo.service

import groovy.com.geo.dao.TestContactDao
import groovy.com.geo.entity.TestContact
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
class TestContactService extends BaseService<TestContact> {
    @Autowired
    public TestContactService(TestContactDao dao) {
        super(dao)
    }

}