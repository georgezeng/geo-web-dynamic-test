package com.geo.entity

import org.hibernate.annotations.GenericGenerator

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * Created by GeorgeZeng on 16/3/3.
 */
@Entity
class TestUser {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id

    def getId() {
        id
    }

    String name
}
