package com.geo.service

import com.geo.entity.User
import com.geo.dao.UserDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by GeorgeZeng on 16/3/6.
 */
@Service
class UserService extends com.geo.service.EntityBasicOPService<User> {
    @Autowired
    private UserDAO dao

    def getDao() {
        dao
    }


}