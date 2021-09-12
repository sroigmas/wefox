package com.wefox.payments.kafka;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.service.KafkaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
@Profile("!test")
public class KafkaConsumer {

  private KafkaService<PaymentDto> paymentsService;

  @KafkaListener(topics = {"${spring.kafka.topics.online}", "${spring.kafka.topics.offline}"},
      groupId = "${spring.kafka.consumer.group-id}")
  public void consume(PaymentDto paymentDto) {
    log.info("Received payment with id '{}' for account '{}'",
        paymentDto.getPaymentId(), paymentDto.getAccountId());
    paymentsService.processMessage(paymentDto);
  }
}
