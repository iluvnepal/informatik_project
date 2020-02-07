import numpy as np
import pandas as pd
import requests
from bs4 import BeautifulSoup

import utils

# https://understat.com/league/EPL/2014

base_url = 'https://understat.com/league'
leagues = ['La_liga', 'EPL', 'Bundesliga', 'Seria_A', 'League_1', 'RFPL']
season = ['2014', '2015', '2016', '2017', '2018', '2019']

history_columns = ['h_a', 'xG', 'xGA', 'npxG', 'npxGA', 'ppda_att', 'ppda_def', 'ppda_allowed_att', 'ppda_allowed_def', 'deep', 'deep_allowed', 'scored', 'missed', 'xpts', 'result', 'date', 'wins', 'draws', 'loses', 'pts', 'npxGD']

# get team data
url = base_url + '/' + leagues[2] + '/' + season[5]
res = requests.get(url)

soup = BeautifulSoup(res.content, 'lxml')

scripts = soup.find_all('script')

teamsData = utils.parse_scripts_to_dict(scripts, 'teamsData')
datesData = utils.parse_scripts_to_dict(scripts, 'datesData')
# playersData = utils.parse_scripts_to_dict(scripts, 'playersData')
# teamsData = json.loads(json_teamsData)

all_teams_history = utils.get_team_name_history_dict(teamsData)

all_matches_stats, training_datas = utils.get_all_matches_history(datesData, teamsData)

for input_array in training_datas[0:10]:
    print(input.label)

