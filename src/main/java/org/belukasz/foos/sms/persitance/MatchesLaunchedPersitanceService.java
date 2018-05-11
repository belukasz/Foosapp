package org.belukasz.foos.sms.persitance;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import java.util.stream.Stream;
import org.belukasz.foos.sms.model.LaunchedPlayer;
import org.jooq.DSLContext;
import org.jooq.Record5;

public class MatchesLaunchedPersitanceService {

  private DSLContext dslContext;

  public MatchesLaunchedPersitanceService(DSLContext dslContext) {
    this.dslContext = dslContext;
  }

  public Stream<LaunchedPlayer> getLaunchedPlayers() {
    return Stream.of(selectPlayers("team_matches.TEAM_1", "teams.PLAYER_1"),
        selectPlayers("team_matches.TEAM_1", "teams.PLAYER_2"),
        selectPlayers("team_matches.TEAM_2", "teams.PLAYER_1"),
        selectPlayers("team_matches.TEAM_2", "teams.PLAYER_2"))
        .flatMap(t -> t).map(LaunchedPlayer::of);
  }

  //"team_matches.TEAM_1" "teams.PLAYER_1"
  private Stream<Record5<String, String, Integer, Integer, Integer>> selectPlayers(
      String teamMatchesSide, String teamsPlayer) {
    return dslContext.select(
        field("persons.FIRST_NAME", String.class), field("persons.LAST_NAME", String.class),
        field("competition_teams.TEAM", Integer.class),
        field("tournament_tables.NO_TABLE", Integer.class),
        field("team_matches.ID_TEAM_MATCH", Integer.class)).from(table("matches"))
        .join(table("team_matches"))
        .on(field("matches.ID_MATCH").eq(field("team_matches.ID_TEAM_MATCH")))
        .join(table("tournament_tables"))
        .on(field("tournament_tables.ID_TABLE").eq(field("team_matches.TABLE_1")))
        .join(table("competition_teams"))
        .on(field(teamMatchesSide).eq(field("competition_teams.ID_COMP_TEAM")))
        .join(table("teams")).on(field("teams.ID_TEAM").eq(field("competition_teams.TEAM")))
        .join(table("persons")).on(field("persons.ID_PERSON").eq(field(teamsPlayer)))
        .where(field("matches.STATUS").eq("C")).fetchStream();
  }

}
