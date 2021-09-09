package com.wefox.payments.kafka;

import com.wefox.payments.service.PaymentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

  @Autowired
  private PaymentsService paymentsService;

  @KafkaListener(topics = {"${spring.kafka.topics.online}", "${spring.kafka.topics.offline}"},
      groupId = "${spring.kafka.consumer.group-id}")
  public void consume(String message) {
    paymentsService.processPayment(message);
  }
}
