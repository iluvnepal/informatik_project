import collections
from typing import List

from Training_data import TrainingData

'''Returns team_id, team_name dict.'''


def get_id_teamname_dict(data):
    all_teams = {}
    for team_id, team_data in data.items():
        all_teams[team_id] = team_data['title']

    return all_teams


'''
Flattens nested dict.
'''


def flatten_nested_dict(nested_dict, parent_key=''):
    items = []
    for k, v in nested_dict.items():
        new_key = parent_key + "_" + k if parent_key else k
        if isinstance(v, collections.MutableMapping):
            items.extend(flatten_nested_dict(v, new_key).items())
        else:
            if k == 'h_a':
                v = 1 if v == 'h' else 0
            items.append((new_key, v))

    return dict(items)


'''
Extracts all matches played by all teams in a league.
'''


def get_team_name_history_dict(data):
    all_teams = get_id_teamname_dict(data)
    teams_history = {}
    for team_id, title in all_teams.items():
        history_list = data[team_id]['history']
        history_flat = []
        for entry in history_list:
            flattened_dict = flatten_nested_dict(entry)
            flattened_dict['id'] = team_id
            history_flat.append(flattened_dict)

        teams_history[team_id] = history_flat

    return dict(teams_history)


def get_all_matches_history(dates_data, teams_data):
    team_name_history_dict = get_team_name_history_dict(teams_data)
    id_name = get_id_teamname_dict(teams_data)
    all_matches = []
    training_data = []
    for match in dates_data:
        from datetime import datetime
        h_team_id = match['h']['id']
        a_team_id = match['a']['id']
        date_played = match['datetime']
        h_team_stats = {}
        a_team_stats = {}

        played_date = datetime.strptime(date_played, '%Y-%m-%d %H:%M:%S')
        if played_date > datetime.today():
            continue

        for match_stat in team_name_history_dict[h_team_id]:
            if match_stat['date'] == date_played:
                h_team_stats = match_stat
                break
        for match_stat in team_name_history_dict[a_team_id]:
            if match_stat['date'] == date_played:
                a_team_stats = match_stat
                break

        if len(a_team_stats.values()) == 0:
            continue

        h_team_stats['xpts_a'] = a_team_stats['xpts']
        training_data.append(TrainingData(h_team_stats))
        all_matches.append(h_team_stats)

    return all_matches, training_data


'''extracts a script where variable_name is assigned with a value.
Returns a python list'''


def parse_scripts_to_dict(scripts, variable_name):
    import json
    string_json_obj = ''
    for element in scripts:
        if variable_name in element.text:
            string_json_obj = element.text.strip()
            break

    start_index = string_json_obj.index("('") + 2
    end_index = string_json_obj.index("')")

    data_json = string_json_obj[start_index:end_index]
    data_json = data_json.encode('utf8').decode('unicode-escape')

    data = json.loads(data_json)

    return data
