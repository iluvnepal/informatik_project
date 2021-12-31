# Football match prediction Webapplication

## Introduction

This project aims to create a simple web application that calculates a result prediction of a football match between two teams. For prediction, an artificial neural network is used. Generally, while using neural network or artificial intelligence, python frameworks dominate the application field. However, there are many lightweight javascript frameworks like brain.js, tf.js, etc that provide a simple solution to training neural networks. In this project, brain.js is being used to create and train a neural network.

## Neural Networks

In simple terms, neural networks can be considered as an intelligent function. Some input data in, and some output data out. Upgraded, in a sense that neural networks adjust themselves with new training data to predict the output with ever increasing precision. That means that quality of (training) data is very important for the accuracy of neural network predictions. Raw match data used in this project are acquired from www.understat.com

This web application offers following features:
- [ ] Choose a league, and two (different) teams.
- [ ] Choose configurations for training the neural network. Options still to be implemented.
- [ ] Get periodic updates on training progress of network.
- [ ] Cancel training and show result.
- [ ] Reset training samples.

To train a neural network, training-data preparation is a very determinant step. In this project, NN(neural network) takes played games hitory stats for two teams as input and should be able to calculate prediction for any two given teams based on their current stats. 

## Past data vs Future data
The problem here arises as a match statistics can be given as an input only when the match has already been played. 

## Depth of statistics
players involved, matches previously played, current form, etc.

## Not in Scope
Data including from only past seasons for single team. e.g. user wants to use average from single season.
possibilities:
- [ ] has the team gotten better or worse comparing average from each individual season.
- [ ] use of caches could increase performance in case of bigger data volume.
