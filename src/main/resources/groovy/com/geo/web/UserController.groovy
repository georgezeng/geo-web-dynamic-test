package groovy.com.geo.web

import com.geo.entity.User
import com.geo.service.UserService
import com.geo.security.UserSessionConstant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpSession

/**
 * Created by GeorgeZeng on 16/3/5.
 */
@Controller
@RequestMapping("/user")
class UserController {
    @Autowired
    private UserService service

//    @RequestMapping("/save")
//    @ResponseBody
//    def save(@RequestBody User user) {
//        SecurityContextHolder.context.authentication.principal
//        if(!user.id) {
//            user.createdBy = currentUser.username
//            user.createdTime = new Date()
//        }
//        user.updatedBy = currentUser.username
//        user.updatedTime = new Date()
//        service.save(user)
//    }
}