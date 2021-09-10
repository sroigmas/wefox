package com.wefox.payments.service;

import com.wefox.payments.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentsService {

  public void processPayment(PaymentDto payment) {
    log.debug("Message received: {}", payment);
  }
}
