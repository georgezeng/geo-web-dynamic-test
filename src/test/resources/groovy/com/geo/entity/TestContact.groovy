package groovy.com.geo.entity

import com.geo.entity.LongKeyEntity

import javax.persistence.Entity

/**
 * Created by GeorgeZeng on 16/3/10.
 */
@Entity(name = "TestContact")
class TestContact extends LongKeyEntity {
    String name
    String phone
}