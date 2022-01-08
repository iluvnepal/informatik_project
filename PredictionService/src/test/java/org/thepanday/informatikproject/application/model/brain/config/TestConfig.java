////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thepanday.informatikproject.application.model.brain.service.IPredictionService;
import org.thepanday.informatikproject.application.model.brain.service.ITrainingDataService;
import org.thepanday.informatikproject.application.model.brain.service.PredictionService;
import org.thepanday.informatikproject.application.model.brain.service.TrainingDataService;
import org.thepanday.informatikproject.util.WebpageScrapingService;

/**
 *
 */
@Configuration
public class TestConfig {

    @Bean
    public IPredictionService getPredictionService() {
        return new PredictionService();
    }

    @Bean
    public ITrainingDataService getTrainingDataService() {
        return new TrainingDataService();
    }

    @Bean
    public WebpageScrapingService getUnderstatDataParser() {
        return new WebpageScrapingService();
    }

}
