package groovy.com.geo.web

import groovy.com.geo.entity.TestContact
import groovy.com.geo.service.TestContactService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
/**
 * Created by GeorgeZeng on 16/2/21.
 */
@Controller
@RequestMapping("/test")
class TestContactController {

    @Autowired
    TestContactService testService

    @RequestMapping("/list")
    @ResponseBody
    def list() {
        testService.list()
    }

    @RequestMapping("/save")
    @ResponseBody
    def save(@RequestBody TestContact contact) {
        testService.save(contact)
    }

}
