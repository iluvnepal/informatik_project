let trainingDataFromServer = null;
let trainingData = {};
let testData = {};
const network = new brain.recurrent.LSTM();

function init() {
    addListener()
    get_training_data()
}

function addListener() {
    let trainButton = document.getElementById('train')
    trainButton.addEventListener('click', train_data, false);
}

function splitTrainingData() {
    let count = trainingDataFromServer.length;
    let testCount = Math.round(count * 0.1);
    let trainCount = count - testCount;
    trainingData = trainingDataFromServer.slice(0, trainCount);
    testData = trainingDataFromServer.slice(trainCount + 1);
    console.log("training to test data split: " + trainingData.length + ":" + testData.length);
}

function train_data() {
    // todo research on Crossvalidate https://golb.hplar.ch/2019/01/machine-learning-with-brain-and-tensorflow-js.html

    let options = {
            activation: 'relu',
            iterations: 200, // the maximum times to iterate the training data --> number greater than 0
            errorThresh: 0.005, // the acceptable error percentage from training data --> number between 0 and 1
            log: true, // true to use console.log, when a function is supplied it is used --> Either true or a function
            logPeriod: 20, // iterations between logging out --> number greater than 0
            learningRate: 0.3, // scales with delta to effect training rate --> number between 0 and 1
            momentum: 0.1, // scales with next layer's change value --> number between 0 and 1
            callback: showProgress(), // a periodic call back that can be triggered while training --> null or function
            callbackPeriod: 20, // the number of iterations through the training data between callback calls --> number greater than 0
            timeout: Infinity, // the max number of milliseconds to train for --> number greater than 0
        }

    ;
    network.trainAsync(trainingData, {
        activation: 'relu',
        iterations: 500,
        log: true,
        logPeriod: 100
    })

    testData.forEach(function (data) {
        let input = data.input;
        let output = data.output;
        console.log("expected: " + output + "\nPrediction: " + network.run(input));
    });
}

function showProgress() {
    // todo get thymeleaf mav object from server and render
}

function testNetwork(dataToTest) {

}

function get_training_data() {
    fetch("/getTrainingData").then(response => response.json()).then(data => {
        trainingDataFromServer = data;
        alert("Data samples fetched from server. Sample count: " + trainingDataFromServer.length)
        splitTrainingData();
    }, showErrorMsg);
}

function showErrorMsg(errorMessage) {
    if (errorMessage === undefined) {
        alert("Error getting data from Server");
    } else {
        alert(errorMessage);
    }
}

// get average stats of all matches of team

window.addEventListener("load", init, false);
