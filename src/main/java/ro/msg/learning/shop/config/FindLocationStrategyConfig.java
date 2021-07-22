package ro.msg.learning.shop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.persistence.LocationRepository;
import ro.msg.learning.shop.persistence.StockRepository;
import ro.msg.learning.shop.service.strategy.FindLocationStrategy;
import ro.msg.learning.shop.service.strategy.FindSingleLocationStrategy;

@Configuration
public class FindLocationStrategyConfig {
    private static final Logger log = LoggerFactory.getLogger(FindLocationStrategyConfig.class);

    @Bean
    public FindLocationStrategy getFindLocationStrategy(LocationRepository locationRepository,
                                                        StockRepository stockRepository) {
        return new FindSingleLocationStrategy(locationRepository);
//        return new FindMostAbundantStrategy(stockRepository);
    }
}
