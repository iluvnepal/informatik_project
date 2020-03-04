'''
xG - expected goals metric, it is a statistical measure of the quality of chances created and conceded. More at understat.com

xG_diff - difference between actual goals scored and expected goals.

npxG - expected goals without penalties and own goals.

xGA - expected goals against.

xGA_diff - difference between actual goals missed and expected goals against.

npxGA - expected goals against without penalties and own goals.

npxGD - difference between "for" and "against" expected goals without penalties and own goals.

ppda_coef - passes allowed per defensive action in the opposition half (power of pressure)

oppda_coef - opponent passes allowed per defensive action in the opposition half (power of opponent's pressure)

deep - passes completed within an estimated 20 yards of goal (crosses excluded)

deep_allowed - opponent passes completed within an estimated 20 yards of goal (crosses excluded)

xpts - expected points

xpts_diff - difference between actual and expected points'''


class TrainingData:
    def __init__(self, match_stat):
        self.home_away = match_stat['h_a']
        self.expected_goal = match_stat['xG']
        self.expected_goal_away_team = match_stat['xGA']
        self.non_penalty_xG = match_stat['npxG']
        self.non_penalty_xG_away_team = match_stat['npxGA']
        # ppda - passes per defensive action in attack
        self.ppda_attack = match_stat['ppda_att']
        self.ppda_defense = match_stat['ppda_def']
        self.ppda_attack_away_team = match_stat['ppda_allowed_att']
        self.ppda_defense_away_team = match_stat['ppda_allowed_def']
        self.deep_passes = match_stat['deep']
        self.deep_passes_away_team = match_stat['deep_allowed']
        self.expected_points = match_stat['xpts']
        self.expected_points_away_team = match_stat['xpts_a']
        self.label = [match_stat['scored'], match_stat['missed']]

    def get_input(self):
        training_dict = self.__dict__.copy()
        training_dict.pop('label')
        return training_dict.values()

    def get_label(self):
        return self.label
