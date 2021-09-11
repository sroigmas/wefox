package com.wefox.payments.service.impl;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.entity.Account;
import com.wefox.payments.entity.Payment;
import com.wefox.payments.repository.AccountRepository;
import com.wefox.payments.repository.PaymentRepository;
import com.wefox.payments.service.KafkaService;
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
  //private RestService restService;
  private PaymentMapper paymentMapper;

  @Override
  public void processMessage(PaymentDto paymentDto) {
    accountRepository.findById(paymentDto.getAccountId()).ifPresentOrElse(account -> {
      savePayment(paymentDto, account);
      log.info("Saved payment with id '{}' for account '{}'",
          paymentDto.getPaymentId(), paymentDto.getAccountId());
    }, () -> {
      log.warn("Missing account with id '{}'", paymentDto.getAccountId());
    });
  }

  private void savePayment(PaymentDto paymentDto, Account account) {
    Payment payment = paymentMapper.dtoToPayment(paymentDto);
    LocalDateTime now = LocalDateTime.now();
    account.setLastPaymentDate(now);
    payment.setCreatedOn(now);
    payment.setAccount(account);
    paymentRepository.save(payment);
  }
}
