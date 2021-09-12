package com.wefox.payments.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorType {
  DATABASE("database"),
  NETWORK("network"),
  OTHER("other");

  private String type;

  ErrorType(String type) {
    this.type = type;
  }

  @JsonValue
  public String getType() {
    return type;
  }
}
