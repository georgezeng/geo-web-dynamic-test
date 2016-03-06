package com.geo.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by GeorgeZeng on 16/3/5.
 */
@Controller
@RequestMapping("/user")
class UserController {

    @RequestMapping("/list")
    @ResponseBody
    def list() {
        "test"
    }
}