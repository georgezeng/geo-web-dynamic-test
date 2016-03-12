package groovy.com.geo.web

import com.geo.security.UserSession
import groovy.com.geo.entity.TestContact
import groovy.com.geo.service.TestContactService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by GeorgeZeng on 16/2/21.
 */
@RestController
@RequestMapping("/testContact")
class TestContactController {

    @Autowired
    TestContactService testService

    @RequestMapping(value = "/list", method = [RequestMethod.GET])
    def list() {
        testService.list()
    }

    @RequestMapping(value = "/save", method = [RequestMethod.POST])
    def save(@RequestBody TestContact updatedContact) {
        def currentUser = UserSession.CONTEXT().get()
        def oldContact = updatedContact
        if(updatedContact.id) {
            oldContact = testService.get(updatedContact.id)
            BeanUtils.copyProperties(updatedContact, oldContact, "createdBy", "createdTime", "updatedBy", "updatedTime")
        } else {
            oldContact.setCreatedBy(currentUser.username)
        }
        oldContact.setUpdatedBy(currentUser.username)
        testService.save(oldContact).toString()
    }

    @RequestMapping(value = "/delete/{id}", method = [RequestMethod.DELETE])
    def delete(@PathVariable("id") Long id) {
        def oldContact = testService.load(id)
        testService.delete(oldContact)
    }

}
