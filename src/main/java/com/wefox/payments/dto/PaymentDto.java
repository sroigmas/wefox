package com.wefox.payments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wefox.payments.util.enums.PaymentType;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(Include.NON_NULL)
public class PaymentDto {

  @JsonProperty("payment_id")
  private String paymentId;

  @JsonProperty("account_id")
  private Integer accountId;

  @JsonProperty("payment_type")
  private PaymentType paymentType;

  @JsonProperty("credit_card")
  private String creditCard;

  private BigDecimal amount;

}
