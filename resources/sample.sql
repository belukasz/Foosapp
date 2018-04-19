select persons.FIRST_NAME, persons.LAST_NAME from matches join team_matches
on matches.ID_MATCH = team_matches.ID_TEAM_MATCH
join competition_teams on team_matches.TEAM_1 = competition_teams.ID_COMP_TEAM
join teams on teams.ID_TEAM = competition_teams.TEAM
join persons on persons.ID_PERSON = teams.PLAYER_1 where matches.STATUS = 'C';

select persons.FIRST_NAME, persons.LAST_NAME from matches join team_matches
on matches.ID_MATCH = team_matches.ID_TEAM_MATCH
join competition_teams on team_matches.TEAM_1 = competition_teams.ID_COMP_TEAM
join teams on teams.ID_TEAM = competition_teams.TEAM
join persons on persons.ID_PERSON = teams.PLAYER_2 where matches.STATUS = 'C' ;

select persons.FIRST_NAME, persons.LAST_NAME from matches join team_matches
on matches.ID_MATCH = team_matches.ID_TEAM_MATCH
join competition_teams on team_matches.TEAM_2 = competition_teams.ID_COMP_TEAM
join teams on teams.ID_TEAM = competition_teams.TEAM
join persons on persons.ID_PERSON = teams.PLAYER_1 where matches.STATUS = 'C';

select persons.FIRST_NAME, persons.LAST_NAME from matches join team_matches
on matches.ID_MATCH = team_matches.ID_TEAM_MATCH
join competition_teams on team_matches.TEAM_2 = competition_teams.ID_COMP_TEAM
join teams on teams.ID_TEAM = competition_teams.TEAM
join persons on persons.ID_PERSON = teams.PLAYER_2 where matches.STATUS = 'C' ;