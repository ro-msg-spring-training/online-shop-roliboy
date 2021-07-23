package ro.msg.learning.shop.service;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;


@Component
public class JPAServiceFactory extends ODataJPAServiceFactory {
    @Autowired
    private LocalContainerEntityManagerFactoryBean factory;


    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
        ODataJPAContext context = this.getODataJPAContext();
        context.setEntityManagerFactory(factory.getObject());
        context.setJPAEdmMappingModel("static/odata-schema.xml");
        context.setPersistenceUnitName("default");
        return context;
    }

    @Override
    public <T extends ODataCallback> T getCallback(final Class<T> callbackInterface) {
        T callback;

        if (callbackInterface.isAssignableFrom(DebugCallback.class)) {
            callback = (T) new DebugCallback();
        } else {
            callback = super.getCallback(callbackInterface);
        }

        return callback;
    }
}