package com.wefox.payments.util;

import com.wefox.payments.util.enums.PaymentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaymentTypeConverterTest {

  private PaymentTypeConverter paymentTypeConverter = new PaymentTypeConverter();

  @Test
  public void convertToDatabaseColumnTest() {
    PaymentType onlinePaymentType = PaymentType.ONLINE;
    String result1 = paymentTypeConverter.convertToDatabaseColumn(onlinePaymentType);
    Assertions.assertEquals(onlinePaymentType.getType(), result1);

    PaymentType offlinePaymentType = PaymentType.ONLINE;
    String result2 = paymentTypeConverter.convertToDatabaseColumn(offlinePaymentType);
    Assertions.assertEquals(offlinePaymentType.getType(), result2);
  }

  @Test
  public void convertToEntityAttributeTest() {
    String onlineType = "online";
    PaymentType result1 = paymentTypeConverter.convertToEntityAttribute(onlineType);
    Assertions.assertEquals(onlineType, result1.getType());

    String offlineType = "offline";
    PaymentType result2 = paymentTypeConverter.convertToEntityAttribute(offlineType);
    Assertions.assertEquals(offlineType, result2.getType());
  }
}
