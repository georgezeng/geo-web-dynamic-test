package groovy.com.geo.config

/**
 * Created by GeorgeZeng on 16/2/21.
 */
class DynamicEntryMapping {
    public static final def map = [
            "testContact": "groovy.com.geo.web.TestContactController",
            "user": "groovy.com.geo.web.controller.UserController",
    ]
}