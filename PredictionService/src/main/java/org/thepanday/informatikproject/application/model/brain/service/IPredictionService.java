////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

/**
 *
 */
public interface IPredictionService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------
    // TODO: structure what this service should do.
    //  create another class to handle training data. An UtilityClass?

    /**
     * Connect to web scraping service and save training data in a file.
     *
     * @return path of filename
     */
    public String getTrainingData();

    /**
     * Look online for new matches statistics.
     */
    public void updateTrainingData();

    public void getAverageMatchHistoryForTeam(String teamTitle);

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

}
