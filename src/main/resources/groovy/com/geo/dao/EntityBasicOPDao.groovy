package com.geo.dao

import org.hibernate.SessionFactory
import com.geo.entity.BaseEntity

/**
 * Created by GeorgeZeng on 16/3/6.
 */

abstract class EntityBasicOPDao<T extends BaseEntity> {
    abstract SessionFactory getSessionFactory()

//    Object invokeMethod(String name, Object args) {
//        switch(name) {
//            case "save":
//                getSessionFactory().currentSession.saveOrUpdate(args)
//                    save()
//
//            break
//        }
//    }

    def save(BaseEntity entity) {
        getSessionFactory().currentSession.saveOrUpdate(entity)
    }
}
