window.addEventListener("load", onLoad, false);

let leagueToTeamsMap = new Map();
const leagues = ["La_liga", "EPL", "Bundesliga", "Serie_A", "Ligue_1", "RFPL"];

function onLoad() {
    getLeaguesAndTeamsFromServer();
    fillLeaguesSelector();
    addListeners();
}

function getLeaguesAndTeamsFromServer() {
    fetch("/getTeamsForLeague").then(response => response.json(), showErrorMsg).then(data => leagueToTeamsMap = data);
}

function fillLeaguesSelector() {
    let league_input_element = document.getElementById('league');

    let emptyOption = document.createElement('option');
    emptyOption.text = '-- select a league --';
    emptyOption.disabled = true;
    emptyOption.selected = true;
    emptyOption.value = "";
    league_input_element.appendChild(emptyOption);

    leagues.forEach(function (league) {
        let leagueToAdd = document.createElement("option");
        leagueToAdd.text = league;
        league_input_element.appendChild(leagueToAdd);
    })
}

function addListeners() {
    // listener for change in league selected
    let leagueSelectElement = document.getElementById('league');
    leagueSelectElement.addEventListener('input', addLeagueTeams, false);
    // reset form
    let resetButton = document.getElementById('reset');
    resetButton.addEventListener('click', onFormReset, false);
}

function onFormReset() {
    removeAllChildren('league');
    removeAllChildren('away_team');
    removeAllChildren('home_team');
    fillLeaguesSelector();
}


function addLeagueTeams(event) {
    console.log("getting league teams...");
    // get the name of league that was selected.
    let selectElement = event.target;
    let selectedLeagueName = selectElement.options[selectElement.selectedIndex].text;
    let teamList = getLeagueTeams(selectedLeagueName);
    let away_team_select_element = document.getElementById('away_team');
    let home_team_select_element = document.getElementById('home_team');
    removeAllChildren(away_team_select_element.id);
    removeAllChildren(home_team_select_element.id);
    if (teamList === undefined) {
        alert("No teams available.");
        return;
    }

    teamList.forEach(function (team) {
        let teamToAdd = document.createElement("option");
        let awayteamToAdd = document.createElement("option");
        teamToAdd.text = team;
        awayteamToAdd.text = team;
        home_team_select_element.appendChild(teamToAdd);
        away_team_select_element.appendChild(awayteamToAdd);
    });
}

function getLeagueTeams(leagueName) {
    return leagueToTeamsMap[leagueName];
}

function removeAllChildren(id) {
    let element = document.getElementById(id);
    while (element.lastElementChild) {
        element.removeChild(element.lastElementChild);
    }
}

function showErrorMsg() {
    alert("Error getting data from Server");
}