package groovy.com.geo.dao

import groovy.com.geo.entity.TestContact
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import javax.annotation.PostConstruct

/**
 * Created by GeorgeZeng on 16/3/3.
 */
@Repository
class TestContactDao extends BaseDao<TestContact> {

    @Autowired
    public TestContactDao(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

    def list() {
        createCriteria().list()
    }

    @PostConstruct
    def createTableIfNecessary() {
        def session = sessionFactory().openStatelessSession()
        try {
            session.createSQLQuery("""
                CREATE TABLE IF NOT EXISTS `${TestContact.class.simpleName}`  (
                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                  `name` varchar(100) NOT NULL,
                  `phone` varchar(100) NOT NULL,
                  `createdTime` datetime NOT NULL,
                  `updatedTime` datetime NOT NULL,
                  `createdBy` varchar(100) NOT NULL,
                  `updatedBy` varchar(100) NOT NULL,
                  PRIMARY KEY (`id`)
                ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
            """).executeUpdate()
        } finally {
            session.close()
        }
    }
}