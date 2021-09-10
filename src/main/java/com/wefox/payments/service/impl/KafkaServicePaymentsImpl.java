package com.wefox.payments.service.impl;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaServicePaymentsImpl implements KafkaService<PaymentDto> {

  @Override
  public void processMessage(PaymentDto payment) {
    log.debug("Message received: {}", payment);
  }
}
