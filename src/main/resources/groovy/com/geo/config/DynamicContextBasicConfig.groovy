package com.geo.config

import com.geo.dynamic.spring.ServletContextHolder
import com.geo.dynamic.spring.aop.DynamicAopProxyFactory
import org.hibernate.SessionFactory
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
import org.springframework.aop.config.AopConfigUtils
import org.springframework.aop.framework.ProxyFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.RootBeanDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.context.support.ServletContextAwareProcessor
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.view.InternalResourceViewResolver
import org.springframework.web.servlet.view.JstlView

import javax.sql.DataSource

/**
 * Created by GeorgeZeng on 16/2/21.
 */
@Configuration
@EnableWebMvc
class DynamicContextBasicConfig {
    @Bean
    public static BeanFactoryPostProcessor dynamicAopSettingPostProcessor() {
        return new DynamicAopSettingPostProcessor()
    }

    @Bean
    public ServletContextAwareProcessor servletContextAwareProcessor() {
        return new ServletContextAwareProcessor(ServletContextHolder.CONTEXT.get())
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        def resolver = new InternalResourceViewResolver()
        resolver.setViewClass(JstlView.class)
        resolver.setPrefix("/WEB-INF/jsp/")
        resolver.setSuffix(".jsp")
        resolver
    }

}

class DynamicAopProxyCreator extends AnnotationAwareAspectJAutoProxyCreator {

    @Override
    protected void customizeProxyFactory(ProxyFactory proxyFactory) {
        proxyFactory.setExposeProxy(true);
        proxyFactory.setAopProxyFactory(new DynamicAopProxyFactory(proxyFactory.getAopProxyFactory()));
    }
}

class DynamicAopSettingPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {

            def proxyClass = AopConfigUtils.@APC_PRIORITY_LIST.find {
                return it.name.contains("DynamicAopProxyCreator")
            }
            if (proxyClass == null) {
                AopConfigUtils.@APC_PRIORITY_LIST.add(DynamicAopProxyCreator.class)
            }
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory
            RootBeanDefinition beanDefinition = new RootBeanDefinition(DynamicAopProxyCreator.class)
            beanDefinition.getPropertyValues().add("order", Ordered.HIGHEST_PRECEDENCE);
            beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition(AopConfigUtils.AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition)
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}


@EnableTransactionManagement
abstract class BasicResourceConfig {
    @Bean
    def LocalSessionFactoryBean sessionFactory(DataSource ds) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean()
        sessionFactory.setAnnotatedClasses(defineHibernateAnnotatedClasses())
        sessionFactory.setDataSource(ds)
        Properties props = defineHibernateProperties()
        sessionFactory.setHibernateProperties(props)
        sessionFactory
    }

    protected abstract Class<?>[] defineHibernateAnnotatedClasses()

    protected abstract Properties defineHibernateProperties()

    @Bean
    def PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager manager = new HibernateTransactionManager()
        manager.setSessionFactory(sessionFactory)
        manager
    }

}