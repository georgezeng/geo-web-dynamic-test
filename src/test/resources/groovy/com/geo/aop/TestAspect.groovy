package groovy.com.geo.aop

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

/**
 * Created by GeorgeZeng on 16/2/21.
 */
@Configuration
@EnableAspectJAutoProxy
@Aspect
class TestAspect {
    def logger = LoggerFactory.getLogger(getClass())

    @Before('execution (* com.geo.service..*(..))')
    def before() {
        logger.info "i am invoked........."
    }
}

