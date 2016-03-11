package groovy.com.geo.dao

import com.geo.dao.BaseDao
import org.hibernate.SessionFactory
import com.geo.entity.BaseEntity
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by GeorgeZeng on 16/3/6.
 */

abstract class BaseDao<T extends BaseEntity<? extends Serializable>> extends com.geo.dao.BaseDao<T> {

    public BaseDao(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

}
