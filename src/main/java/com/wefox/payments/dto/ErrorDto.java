package com.wefox.payments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wefox.payments.util.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ErrorDto {

  @JsonProperty("payment_id")
  private String paymentId;

  @JsonProperty("error_type")
  private ErrorType errorType;

  @JsonProperty("error_description")
  private String errorDescription;

}
