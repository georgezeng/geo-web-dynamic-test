package groovy.com.geo.web

import com.fasterxml.jackson.annotation.JsonView
import groovy.com.geo.service.TestUserService
import com.geo.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
/**
 * Created by GeorgeZeng on 16/2/21.
 */
@Controller
@RequestMapping("/test")
class TestUserController {

    @Autowired
    TestUserService testService

    @RequestMapping("/list")
    @ResponseBody
    @JsonView(User.SimpleView.class)
    def list() {
        testService.list()
    }

    @RequestMapping("/save")
    @ResponseBody
    def save(@RequestBody User user) {
        testService.save(user)
    }

    @RequestMapping("/current")
    @ResponseBody
    def current() {
        SecurityContextHolder.context.getAuthentication().principal
    }
}
