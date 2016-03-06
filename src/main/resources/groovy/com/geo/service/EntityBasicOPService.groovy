package com.geo.service

import com.geo.entity.BaseEntity

/**
 * Created by GeorgeZeng on 16/3/6.
 */
abstract class EntityBasicOPService<T extends BaseEntity> {
    abstract def getDao()

//    Object invokeMethod(String name, Object args) {
//        switch(name) {
//            case "save": getDao.save(args)
//        }
//    }

    def save(T entity) {
        dao.save(entity)
    }

}