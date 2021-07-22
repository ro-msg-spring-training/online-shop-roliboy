package ro.msg.learning.shop.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

public class CsvMessageConverter extends AbstractHttpMessageConverter<Object> {
    public static final MediaType MEDIA_TYPE = new MediaType("text", "csv", StandardCharsets.UTF_8);

    public CsvMessageConverter() {
        super(MEDIA_TYPE);
    }

    protected boolean supports(Class<?> klass) {
        return false;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage output) throws IOException, HttpMessageNotWritableException {
        output.getHeaders().setContentType(MEDIA_TYPE);
        var out = output.getBody();

        // TODO: check for http not found error and stuff, otherwise this will try to convert it to csv
        if (object instanceof Collection<?> objects) {
            var instance = objects.stream().findFirst();
            if (instance.isPresent()) {
                CsvConverter.toCsv(instance.get().getClass(), objects, out);
            } else {
                System.out.println("csv, n-ai csv");
            }
        } else {
            CsvConverter.toCsv(object.getClass(), Collections.singleton(object), out);
        }

        out.close();
    }
}
