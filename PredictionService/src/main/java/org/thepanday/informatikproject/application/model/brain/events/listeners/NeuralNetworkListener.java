////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.05.2022.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.events.listeners;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.events.NeuralNetworkEvent;
import org.neuroph.core.events.NeuralNetworkEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thepanday.informatikproject.application.model.brain.service.ITrainingDataService;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public class NeuralNetworkListener implements NeuralNetworkEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(NeuralNetworkListener.class);

    @Autowired
    private ITrainingDataService mTrainingDataService;

    @Override
    public void handleNeuralNetworkEvent(NeuralNetworkEvent event) {
        final NeuralNetworkEvent.Type eventType = event.getEventType();

        switch (eventType) {
            case CALCULATED:
                this.logAfterCalculation(event);
                break;
            default:
                break;
        }
    }

    private void logAfterCalculation(NeuralNetworkEvent event) {
        final NeuralNetwork<?> network = (NeuralNetwork<?>) event.getSource();
        final double[] output = mTrainingDataService.denormalizeOutput(network.getOutput());
        final List<Double> netInput = network
            .getInputNeurons()
            .stream()
            .map(Neuron::getNetInput)
            .collect(Collectors.toList());
        LOGGER.info(MessageFormat.format("Input: {0}, Output: {1}", netInput, output));
    }

}
