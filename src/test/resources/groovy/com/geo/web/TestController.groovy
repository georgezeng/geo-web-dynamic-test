package com.geo.web

import com.geo.service.TestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
/**
 * Created by GeorgeZeng on 16/2/21.
 */
@Controller
@RequestMapping("/test")
class TestController {

    @Autowired
    TestService testService

    @RequestMapping("/list")
    @ResponseBody
    def list() {
        testService.list()
    }

    @RequestMapping("/save")
    @ResponseBody
    def save(@RequestBody com.geo.entity.TestUser user) {
        testService.save(user)
    }
}
