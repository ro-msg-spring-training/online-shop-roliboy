package ro.msg.learning.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import ro.msg.learning.shop.converter.CsvMessageConverter;

@Configuration
public class HttpMessageConverterConfig {
    @Bean
    public HttpMessageConverter<Object> createCsvConverter() {
        return new CsvMessageConverter();
    }
}
