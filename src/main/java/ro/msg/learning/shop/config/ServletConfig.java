package ro.msg.learning.shop.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.servlet.JPAServlet;

@Configuration
public class ServletConfig {
    @Bean
    public ServletRegistrationBean<JPAServlet> odataServlet(JPAServlet servlet) {
        return new ServletRegistrationBean<>(servlet, "/odata/*");
    }
}