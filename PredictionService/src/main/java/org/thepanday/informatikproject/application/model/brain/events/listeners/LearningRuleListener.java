////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 24.05.2022.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.events.listeners;

import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.SupervisedLearning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;

public class LearningRuleListener implements LearningEventListener {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------
    private static final Logger LOGGER = LoggerFactory.getLogger(LearningRuleListener.class);
    private long mPrintInterval = 50;
    private final AtomicLong mCurrentIteration = new AtomicLong(0);
    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    @Override
    public void handleLearningEvent(LearningEvent event) {
        mCurrentIteration.incrementAndGet();
        if (mCurrentIteration.get() % mPrintInterval == 0) {
            SupervisedLearning learningRule = (SupervisedLearning) event.getSource();
            LOGGER.info(MessageFormat.format("iteration: {0}, networkError: {1}, error change iteration count: {2}",
                                             learningRule.getCurrentIteration(),
                                             learningRule.getTotalNetworkError(),
                                             learningRule.getMinErrorChangeIterationsCount()));
        }
    }

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

    public long getPrintInterval() {
        return mPrintInterval;
    }

    public void setPrintInterval(long printInterval) {
        mPrintInterval = printInterval;
    }
}
