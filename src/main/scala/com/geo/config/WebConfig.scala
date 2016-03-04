package com.geo.config

import org.springframework.context.annotation.{ComponentScan, Configuration}
import org.springframework.web.servlet.config.annotation.EnableWebMvc

/**
  * Created by GeorgeZeng on 16/2/19.
  */

trait WebConfigModel {

}

@Configuration
@ComponentScan(basePackages = Array("com.geo.web"))
@EnableWebMvc
class WebConfig extends WebConfigModel {

}
