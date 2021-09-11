package com.wefox.payments.util;

import com.wefox.payments.util.enums.PaymentType;

import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentTypeConverter implements AttributeConverter<PaymentType, String> {

  @Override
  public String convertToDatabaseColumn(PaymentType paymentType) {
    if (Objects.isNull(paymentType)) {
      return null;
    }
    return paymentType.getType();
  }

  @Override
  public PaymentType convertToEntityAttribute(String type) {
    if (Objects.isNull(type)) {
      return null;
    }
    return Stream.of(PaymentType.values())
        .filter(paymentType -> paymentType.getType().equals(type))
        .findFirst().orElse(null);
  }
}
