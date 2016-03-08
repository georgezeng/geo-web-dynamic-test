package groovy.com.geo.dao

import com.geo.entity.User
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Created by GeorgeZeng on 16/3/3.
 */
@Repository
class TestUserDAO {
    @Autowired
    SessionFactory sessionFactory

    def list() {
        sessionFactory.getCurrentSession().createCriteria(User.class).list()
    }

    def save(user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user)
    }

}