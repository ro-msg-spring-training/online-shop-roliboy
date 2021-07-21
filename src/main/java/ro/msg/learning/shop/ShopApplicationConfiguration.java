package ro.msg.learning.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.service.strategy.FindLocationStrategy;
import ro.msg.learning.shop.service.strategy.FindMostAbundantStrategy;
import ro.msg.learning.shop.service.strategy.FindSingleLocationStrategy;

import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class ShopApplicationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ShopApplicationConfiguration.class);

    @Bean
    public FindLocationStrategy getFindLocationStrategy() {
//        return new FindSingleLocationStrategy();
        return new FindMostAbundantStrategy();
//        if (ThreadLocalRandom.current().nextBoolean()) {
//            log.info("Using 'most abundant' strategy for finding order locations");
//            return new FindMostAbundantStrategy();
//        } else {
//            log.info("Using 'single location' strategy for finding order locations");
//            return new FindSingleLocationStrategy();
//        }
    }
}
