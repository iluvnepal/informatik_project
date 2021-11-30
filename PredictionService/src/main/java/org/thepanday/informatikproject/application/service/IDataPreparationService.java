////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 30.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.service;

import org.thepanday.informatikproject.common.util.jsonentities.TrainingData;

/**
 *
 */
public interface IDataPreparationService {

    public void convertJsonToCsv(String jsonFilename);

    /**
     * normalises inputs and output
     * @param trainingData
     */
    public void normaliseTrainingData(TrainingData trainingData);

}
