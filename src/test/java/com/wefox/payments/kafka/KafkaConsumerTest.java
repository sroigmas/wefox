package com.wefox.payments.kafka;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.service.impl.KafkaServicePaymentsImpl;
import com.wefox.payments.util.enums.PaymentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerTest {

  @Mock
  private KafkaServicePaymentsImpl paymentsService;

  @InjectMocks
  private KafkaConsumer kafkaConsumer;

  @Test
  public void consumeOnlinePaymentTest() {
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(123);
    paymentDto.setPaymentType(PaymentType.ONLINE);
    paymentDto.setAmount(new BigDecimal(50));

    kafkaConsumer.consumeOnlinePayment(paymentDto);

    Mockito.verify(paymentsService).processMessage(paymentDto);
  }

  @Test
  public void consumeOfflinePaymentTest() {
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(123);
    paymentDto.setPaymentType(PaymentType.OFFLINE);
    paymentDto.setAmount(new BigDecimal(50));

    kafkaConsumer.consumeOfflinePayment(paymentDto);

    Mockito.verify(paymentsService).processMessage(paymentDto);
  }
}
