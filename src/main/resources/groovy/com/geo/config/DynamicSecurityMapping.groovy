package groovy.com.geo.config

/**
 * Created by GeorgeZeng on 16/3/6.
 */
class DynamicSecurityMapping {
    public static final def map = [
//            "/user/**": "permitAll",
            "/**": "hasRole('ROLE_SUPER_ADMIN')",
    ]
}