package groovy.com.geo.service

import com.geo.entity.BaseEntity
import com.geo.service.BaseService
import groovy.com.geo.dao.BaseDao
import org.springframework.transaction.annotation.Transactional

/**
 * Created by GeorgeZeng on 16/3/6.
 */
@Transactional
abstract class BaseService<T extends BaseEntity<? extends Serializable>> extends com.geo.service.BaseService<T> {
    public BaseService(BaseDao<T> dao) {
        super(dao)
    }

    def invokeMethod(String name, Object args) {
        dao().invokeMethod(name, args)
    }
}