package com.wefox.payments.service.impl;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.entity.Account;
import com.wefox.payments.entity.Payment;
import com.wefox.payments.repository.AccountRepository;
import com.wefox.payments.repository.PaymentRepository;
import com.wefox.payments.service.KafkaService;
import com.wefox.payments.service.RestService;
import com.wefox.payments.util.enums.ErrorType;
import com.wefox.payments.util.enums.PaymentType;
import com.wefox.payments.util.mapper.PaymentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaServicePaymentsImpl implements KafkaService<PaymentDto> {

  private AccountRepository accountRepository;
  private PaymentRepository paymentRepository;
  private RestService restService;
  private PaymentMapper paymentMapper;

  @Override
  public void processMessage(PaymentDto paymentDto) {
    accountRepository.findById(paymentDto.getAccountId()).ifPresentOrElse(account -> {
      if (PaymentType.OFFLINE.equals(paymentDto.getPaymentType())
          || restService.isValidPayment(paymentDto)) {
        savePayment(paymentDto, account);
      }
    }, () -> {
      String message = String.format("Missing account with id '%d'", paymentDto.getAccountId());
      log.warn(message);
      restService.logError(paymentDto.getPaymentId(), ErrorType.OTHER, message);
    });
  }

  private void savePayment(PaymentDto paymentDto, Account account) {
    Payment payment = paymentMapper.dtoToPayment(paymentDto);
    LocalDateTime now = LocalDateTime.now();
    account.setLastPaymentDate(now);
    payment.setCreatedOn(now);
    payment.setAccount(account);

    try {
      paymentRepository.save(payment);
      log.info("Saved payment with id '{}' for account '{}'",
          paymentDto.getPaymentId(), paymentDto.getAccountId());
    } catch (Exception e) {
      log.error("Error saving payment with id '{}' for account '{}'",
          paymentDto.getPaymentId(), paymentDto.getAccountId(), e);
      restService.logError(paymentDto.getPaymentId(), ErrorType.DATABASE, e.getMessage());
    }
  }
}
