package groovy.com.geo.config

import com.geo.config.BasicResourceConfig
import org.springframework.context.annotation.Configuration

/**
 * Created by GeorgeZeng on 16/3/3.
 */
@Configuration
class TestResourceConfig extends BasicResourceConfig {

    @Override
    def Set<Class<?>> defineHibernateAnnotatedClasses() {
        new LinkedHashSet<Class<?>>()
    }

}
