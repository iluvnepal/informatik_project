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
# def flatten(d, parent_key='', sep='_'):
    items = []
    for k, v in history_dict.items():
        new_key = parent_key + "_" + k if parent_key else k
        if isinstance(v, collections.MutableMapping):
            items.extend(flatten_and_normalise_dict(v, new_key).items())
        else:
            items.append((new_key, v))
    return dict(items)


def get_team_name_history_dict(data):
    all_teams = get_all_team_names(data)
    teams_history = {}
    for id, title in all_teams.items():
        history_list = data[id]['history']
        history_flat = {}
        for entry in history_list:
            history_flat.update(flatten_and_normalise_dict(entry))

        history_flat['h_a'] = 0 if history_flat['h_a'] == 'h' else 1

        teams_history[title] = history_flat

    return teams_history


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
