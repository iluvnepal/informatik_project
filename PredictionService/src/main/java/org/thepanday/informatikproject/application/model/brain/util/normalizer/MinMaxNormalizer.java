////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 01.06.2022.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.util.normalizer;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.exceptions.VectorSizeMismatchException;
import org.neuroph.util.data.norm.MaxMinNormalizer;

import java.text.MessageFormat;

public class MinMaxNormalizer extends MaxMinNormalizer {

    public MinMaxNormalizer(DataSet dataSet) {
        super(dataSet);
    }

    public double[] denormalizeOutput(double[] vector) {
        double[] denormalizedVector = new double[vector.length];
        double[] maxOut = this.getMaxOut();
        double[] minOut = this.getMinOut();
        if (vector.length != maxOut.length) {
            throw new VectorSizeMismatchException(MessageFormat.format("Given output vector's length {0} does not match dataset's output length {1}.",
                                                                       vector.length,
                                                                       maxOut.length));
        }
        for (int i = 0; i < vector.length; i++) {
            denormalizedVector[i] = (vector[i] * (maxOut[i] - minOut[i])) + minOut[i];
        }
        return denormalizedVector;
    }

    private double[] normalizeMaxMin(double[] vector, double[] min, double[] max) {
        double[] normalizedVector = new double[vector.length];

        for (int i = 0; i < vector.length; i++) {
            normalizedVector[i] = (vector[i] - min[i]) / (max[i] - min[i]);
        }

        return normalizedVector;
    }
}
