package org.belukasz.foos.sms.model;

import org.jooq.Record5;

public class LaunchedPlayer {

  private String firstName;
  private String lastName;
  private Integer teamId;
  private Integer tableNumber;
  private Integer matchId;

  public static LaunchedPlayer of(Record5<String, String, Integer, Integer, Integer> record){
    return new LaunchedPlayer(record.component1(), record.component2(),
        record.component3(), record.component4(), record.component5());
  }

  private LaunchedPlayer(String firstName, String lastName, Integer teamId,
      Integer tableNumber, Integer matchId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.teamId = teamId;
    this.tableNumber = tableNumber;
    this.matchId = matchId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Integer getTeamId() {
    return teamId;
  }

  public void setTeamId(Integer teamId) {
    this.teamId = teamId;
  }

  public Integer getTableNumber() {
    return tableNumber;
  }

  public void setTableNumber(Integer tableNumber) {
    this.tableNumber = tableNumber;
  }

  public Integer getMatchId() {
    return matchId;
  }

  public void setMatchId(Integer matchId) {
    this.matchId = matchId;
  }

  @Override
  public String toString() {
    return "LaunchedPlayer{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", teamId=" + teamId +
        ", tableNumber=" + tableNumber +
        ", matchId=" + matchId +
        '}';
  }
}
