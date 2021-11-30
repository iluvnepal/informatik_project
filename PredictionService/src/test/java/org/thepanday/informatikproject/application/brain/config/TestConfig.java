////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.brain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thepanday.informatikproject.application.brain.IPredictionService;
import org.thepanday.informatikproject.application.brain.PredictionService;
import org.thepanday.informatikproject.application.service.ITrainingDataService;
import org.thepanday.informatikproject.application.service.TrainingDataService;
import org.thepanday.informatikproject.common.util.UnderstatDataParser;

/**
 *
 */
@Configuration
public class TestConfig {

    @Bean
    public IPredictionService getPredictionService(){
        return new PredictionService();
    }

    @Bean
    public ITrainingDataService getTrainingDataService(){
        return new TrainingDataService();
    }

    @Bean
    public UnderstatDataParser getUnderstatDataParser() {
        return new UnderstatDataParser();
    }

}
