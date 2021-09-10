package com.wefox.payments.kafka;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.enums.PaymentType;
import com.wefox.payments.service.PaymentsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerTest {

  @Mock
  private PaymentsService paymentsService;

  @InjectMocks
  private KafkaConsumer kafkaConsumer;

  @Test
  public void consumeTest() {
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(123);
    paymentDto.setPaymentType(PaymentType.ONLINE);
    paymentDto.setAmount(50);

    kafkaConsumer.consume(paymentDto);

    Mockito.verify(paymentsService).processPayment(paymentDto);
  }
}
