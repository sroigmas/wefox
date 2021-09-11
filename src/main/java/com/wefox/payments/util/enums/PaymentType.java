package com.wefox.payments.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentType {
  ONLINE("online"),
  OFFLINE("offline");

  private String type;

  PaymentType(String type) {
    this.type = type;
  }

  @JsonValue
  public String getType() {
    return type;
  }
}
