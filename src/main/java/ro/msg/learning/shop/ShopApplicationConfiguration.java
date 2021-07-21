package ro.msg.learning.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import ro.msg.learning.shop.converter.CsvMessageConverter;
import ro.msg.learning.shop.service.strategy.FindLocationStrategy;
import ro.msg.learning.shop.service.strategy.FindMostAbundantStrategy;
import ro.msg.learning.shop.service.strategy.FindSingleLocationStrategy;

@Configuration
public class ShopApplicationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ShopApplicationConfiguration.class);

    @Bean
    public FindLocationStrategy getFindLocationStrategy() {
        return new FindSingleLocationStrategy();
//        return new FindMostAbundantStrategy();
    }

    @Bean
    public HttpMessageConverter<Object> createCsvConverter() {
        return new CsvMessageConverter();
    }
}
