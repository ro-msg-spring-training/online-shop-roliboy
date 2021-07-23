package ro.msg.learning.shop.servlet;

import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.core.servlet.ODataServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.service.JPAServiceFactory;

import javax.servlet.http.HttpServletRequest;

@Component
public class JPAServlet extends ODataServlet {
    private final transient ApplicationContext context;

    @Autowired
    public JPAServlet(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected ODataServiceFactory getServiceFactory(HttpServletRequest request) {
        return context.getBean(JPAServiceFactory.class);
    }
}