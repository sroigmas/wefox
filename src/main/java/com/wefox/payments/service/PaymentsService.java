package com.wefox.payments.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentsService {

  public void processPayment(String payment) {
    log.debug("Message received: {}", payment);
  }
}
