import collections


def get_team_id(team_name, teams):
    for id, name in teams.items():
        if name == team_name:
            return id
    return None


def get_all_team_names(data):
    all_teams = {}
    for team_id, team_data in data.items():
        all_teams[team_id] = team_data['title']

    return all_teams


def flatten_and_normalise_dict(history_dict, parent_key=''):
    '''Returns all game historys of a team as a simple dict by flattening inner dictionaries recursively.'''
    items = []
    for k, v in history_dict.items():
        new_key = parent_key + "_" + k if parent_key else k
        if isinstance(v, collections.MutableMapping):
            items.extend(flatten_and_normalise_dict(v, new_key).items())
        else:
            if k == 'h_a':
                v = 1 if v == 'h' else 0
            items.append((new_key, v))

    return dict(items)


def get_team_name_history_dict(data):
    all_teams = get_all_team_names(data)
    teams_history = {}
    for id, title in all_teams.items():
        history_list = data[id]['history']
        history_flat = []
        for entry in history_list:
            flattened_dict = flatten_and_normalise_dict(entry)
            flattened_dict['id'] = id
            history_flat.append(flattened_dict)

        teams_history[id] = history_flat
        # teams_history[title] = history_flat

    return dict(teams_history)

def get_all_matches_history(dates_data, teams_data):
    team_name_history_dict = get_team_name_history_dict(teams_data)
    all_matches = []
    for match in dates_data:
        h_team_id = match['h']['id']
        a_team_id = match['a']['id']
        date_played = match['datetime']
        h_team_stats = {}
        a_team_stats = {}
        for match_stat in team_name_history_dict[h_team_id]:
            if match_stat['date'] == date_played:
                h_team_stats = match_stat
                break
        for match_stat in team_name_history_dict[a_team_id]:
            if match_stat['date'] == date_played:
                a_team_stats = match_stat
                break

        match_data = {h_team_id: h_team_stats,
                      a_team_id: a_team_stats,
                      date_played: date_played,
                      'h_goals': match['goals']['h'],
                      'a_goals': match['goals']['a']}
        all_matches.append(match_data)
    # todo the match stats of opponent team is also in the team-match stat. trim the training data.
    return all_matches




def parse_scripts_to_dict(scripts, variable_name):
    '''extracts a script where variable_name is assigned with a value.
    Returns a python list'''
    import json
    string_json_obj = ''
    for element in scripts:
        if variable_name in element.text:
            string_json_obj = element.text.strip()

    start_index = string_json_obj.index("('") + 2
    end_index = string_json_obj.index("')")

    data_json = string_json_obj[start_index:end_index]
    data_json = data_json.encode('utf8').decode('unicode-escape')

    data = json.loads(data_json)

    return data
