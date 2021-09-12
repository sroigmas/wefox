package com.wefox.payments.kafka;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.service.impl.KafkaServicePaymentsImpl;
import com.wefox.payments.util.enums.PaymentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerTest {

  @Mock
  private KafkaServicePaymentsImpl paymentsService;

  @InjectMocks
  private KafkaConsumer kafkaConsumer;

  private static PaymentDto paymentDto;

  @BeforeAll
  static void init() {
    paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(123);
    paymentDto.setAmount(new BigDecimal(50));
  }

  @Test
  public void consumeOnlinePaymentTest() {
    paymentDto.setPaymentType(PaymentType.ONLINE);

    kafkaConsumer.consumeOnlinePayment(paymentDto);

    Mockito.verify(paymentsService).processMessage(paymentDto);
  }

  @Test
  public void consumeOfflinePaymentTest() {
    paymentDto.setPaymentType(PaymentType.OFFLINE);

    kafkaConsumer.consumeOfflinePayment(paymentDto);

    Mockito.verify(paymentsService).processMessage(paymentDto);
  }

  @Test
  public void consumePaymentTest() {
    paymentDto.setPaymentType(PaymentType.OFFLINE);

    ReflectionTestUtils.invokeMethod(kafkaConsumer, "consumePayment", paymentDto);

    Mockito.verify(paymentsService).processMessage(paymentDto);
  }
}
