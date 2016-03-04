package com.geo.test

import com.geo.config.{ResourceConfigModel, WebConfigModel}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.config.annotation.{EnableWebMvc, ResourceHandlerRegistry, WebMvcConfigurerAdapter}

/**
  * Created by GeorgeZeng on 16/2/19.
  */
@SpringBootApplication
@ComponentScan(basePackages = Array("com.geo.web"))
@EnableWebMvc
class TestConfig extends WebMvcConfigurerAdapter with WebConfigModel with ResourceConfigModel {

  override def addResourceHandlers( registry:ResourceHandlerRegistry):Unit = {
    registry.addResourceHandler("/**")
      .addResourceLocations("/")
      .setCachePeriod(0)
  }

}


object TestApp extends App {
  System.setProperty("ENV_SYSTEM", "test")
  SpringApplication.run(classOf[TestConfig])
  while (true) {}
}
