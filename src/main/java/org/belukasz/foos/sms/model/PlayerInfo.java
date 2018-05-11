package org.belukasz.foos.sms.model;

import com.opencsv.bean.CsvBindByName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PlayerInfo {

  @CsvBindByName(column = "name", required = true)
  private String name;
  @CsvBindByName(column = "surname", required = true)
  private String surname;
  @CsvBindByName(column = "number", required = true)
  private String number;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return "PlayerInfo{" +
        "name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", number='" + number + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PlayerInfo that = (PlayerInfo) o;

    return new EqualsBuilder()
        .append(name, that.name)
        .append(surname, that.surname)
        .append(number, that.number)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(name)
        .append(surname)
        .append(number)
        .toHashCode();
  }
}
