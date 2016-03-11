package com.geo.filter

import java.io.{BufferedReader, FileReader}
import javax.servlet.http.HttpServletRequest
import javax.servlet.{FilterChain, ServletRequest, ServletResponse}

import com.typesafe.config.Config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

import scala.collection.JavaConversions._

/**
  * Created by GeorgeZeng on 16/3/10.
  */
@Component
class StaticResourceFilter @Autowired()(config: Config) extends GenericFilterBean {
  private val staticResourceExtensions = config.getConfig("resource").getStringList("extensions")

  override def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {
    val httpReq = request.asInstanceOf[HttpServletRequest]
    def isMatch(): Boolean = {
      for (extension <- staticResourceExtensions) {
        val matcher = new AntPathRequestMatcher("/**." + extension)
        if (matcher.matches(httpReq)) {
          return true
        }
      }
      false
    }

    if (isMatch) {
      val reader = new BufferedReader(new FileReader(httpReq.getServletContext.getRealPath("/") + httpReq.getRequestURI))
      val out = response.getWriter
      try {
        var line = reader.readLine()
        while (line != null) {
          out.write(line)
          line = reader.readLine()
        }
        out.flush()
      } finally {
        out.close()
      }
    } else {
      chain.doFilter(request, response)
    }
  }
}
