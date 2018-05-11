package org.belukasz.foos.sms.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Message {

  private String content;
  private String number;

  public Message(String content, String number) {
    this.content = content;
    this.number = number;
  }

  public String getContent() {
    return content;
  }

  public String getNumber() {
    return number;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Message message = (Message) o;

    return new EqualsBuilder()
        .append(content, message.content)
        .append(number, message.number)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(content)
        .append(number)
        .toHashCode();
  }

  @Override
  public String toString() {
    return "Message{" +
        "content='" + content + '\'' +
        ", number='" + number + '\'' +
        '}';
  }
}
