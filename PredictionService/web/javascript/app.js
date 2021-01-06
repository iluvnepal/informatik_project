const brain = require('lib/node_modules/nodebrain.js');

const network = new brain.NeuralNetwork();
// todo: generate data to train
//  Q: what are the team general stats that is available in understat page? create a field for the current stats?
// Question: What are the similar stats between match stats and current team stat? use them as input data.
//  input format : [ <match stats to include>] output format: [ goals, goals ]

function train_data(){

    network.train([
        {input: [], output: []},
        {input: [], output: []},
        {input: [], output: []},
        {input: [], output: []},
        {input: [], output: []},
        {input: [], output: []}
    ])
}

function get_training_data(){
    const data = require("data.json")
}

function get_current_teams(){
    document.getElementById()
}