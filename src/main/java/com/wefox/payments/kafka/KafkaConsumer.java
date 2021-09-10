package com.wefox.payments.kafka;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.service.PaymentsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

  private PaymentsService paymentsService;

  @KafkaListener(topics = {"${spring.kafka.topics.online}", "${spring.kafka.topics.offline}"},
      groupId = "${spring.kafka.consumer.group-id}")
  public void consume(PaymentDto paymentDto) {
    log.debug("Received payment message with id '{}' for account '{}'",
        paymentDto.getPaymentId(), paymentDto.getAccountId());
    paymentsService.processPayment(paymentDto);
  }
}
