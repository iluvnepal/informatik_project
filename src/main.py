import numpy as np
import pandas as pd
import requests
from bs4 import BeautifulSoup

import utils

# https://understat.com/league/EPL/2014

base_url = 'https://understat.com/league'
leagues = ['La_liga', 'EPL', 'Bundesliga', 'Serie_A', 'Ligue_1', 'RFPL']
season = ['2014', '2015', '2016', '2017', '2018', '2019']

history_columns = ['h_a', 'xG', 'xGA', 'npxG', 'npxGA', 'ppda_att', 'ppda_def', 'ppda_allowed_att', 'ppda_allowed_def', 'deep', 'deep_allowed', 'scored', 'missed', 'xpts', 'result', 'date', 'wins', 'draws', 'loses', 'pts', 'npxGD']

# get team data
url = ''
all_matches_data = []
for league in leagues:
    url = base_url + '/' + league
    for year in season:
        print("league: " + league + "\tyear: " + year)
        current_url = url + '/' + year
        res = requests.get(current_url)
        if res.status_code == 404:
            print("Site could not be reached: " + current_url)
            continue
        soup = BeautifulSoup(res.content, 'lxml')
        scripts = soup.find_all('script')

        teamsData = utils.parse_scripts_to_dict(scripts, 'teamsData')
        datesData = utils.parse_scripts_to_dict(scripts, 'datesData')

        all_matches_stats, training_datas = utils.get_all_matches_history(datesData, teamsData)
        all_matches_data.extend(training_datas)
        import csv
        w = csv.writer(open("training_data.csv", "w"))
        for td in training_datas:
            w.writerow(td.get_input())


import json
td_list = []
for td in training_datas:
    td_list.append({'input': td.get_input(), 'output': td.get_label()})

# training_json = []
# for element in td_list:
#     training_json.append(json.dumps(list(element.values()), ))
#
# print(len(training_json))
# # with open('data.json', 'w') as f:
# #     json.dumps()





