package com.wefox.payments.service.impl;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.entity.Account;
import com.wefox.payments.entity.Payment;
import com.wefox.payments.repository.AccountRepository;
import com.wefox.payments.repository.PaymentRepository;
import com.wefox.payments.service.KafkaService;
import com.wefox.payments.util.enums.PaymentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class KafkaServicePaymentsImplIntTest {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private KafkaService<PaymentDto> kafkaService;

  @Test
  public void processMessageTest() {
    String accountEmail = "test@gmail.com";
    String paymentId = "123";
    PaymentType paymentType = PaymentType.OFFLINE;
    BigDecimal paymentAmount = new BigDecimal(50);

    Account account = new Account();
    account.setEmail(accountEmail);
    account = accountRepository.save(account);
    Integer accountId = account.getAccountId();
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId(paymentId);
    paymentDto.setAccountId(accountId);
    paymentDto.setPaymentType(paymentType);
    paymentDto.setAmount(paymentAmount);

    kafkaService.processMessage(paymentDto);

    Optional<Payment> optional = paymentRepository.findById(paymentId);
    Assertions.assertTrue(optional.isPresent());
    Payment payment = optional.get();
    Assertions.assertEquals(paymentId, payment.getPaymentId());
    Assertions.assertEquals(paymentType, payment.getPaymentType());
    Assertions.assertEquals(0, paymentAmount.compareTo(payment.getAmount()));
    Assertions.assertNull(payment.getCreditCard());
    Assertions.assertNotNull(payment.getCreatedOn());
    Account paymentAccount = payment.getAccount();
    Assertions.assertNotNull(paymentAccount);
    Assertions.assertEquals(accountId, paymentAccount.getAccountId());
    Assertions.assertEquals(accountEmail, paymentAccount.getEmail());
  }
}
