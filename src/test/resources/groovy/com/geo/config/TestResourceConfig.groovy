package groovy.com.geo.config

import com.geo.config.BasicResourceConfig
import com.geo.entity.Role
import com.geo.entity.RoleAuthority
import com.geo.entity.User
import com.geo.entity.UserRole
import groovy.com.geo.entity.TestContact
import org.springframework.context.annotation.Configuration

/**
 * Created by GeorgeZeng on 16/3/3.
 */
@Configuration
class TestResourceConfig extends BasicResourceConfig {

    @Override
    def Class<?>[] defineHibernateAnnotatedClasses() {
        return [TestContact.class]
    }

}
