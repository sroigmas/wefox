package com.wefox.payments.service.impl;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.entity.Account;
import com.wefox.payments.entity.Payment;
import com.wefox.payments.repository.AccountRepository;
import com.wefox.payments.repository.PaymentRepository;
import com.wefox.payments.service.RestService;
import com.wefox.payments.util.enums.ErrorType;
import com.wefox.payments.util.enums.PaymentType;
import com.wefox.payments.util.mapper.PaymentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class KafkaServicePaymentsImplTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private PaymentRepository paymentRepository;

  @Mock
  private RestService restService;

  @Mock
  private PaymentMapper paymentMapper;

  @InjectMocks
  private KafkaServicePaymentsImpl kafkaService;

  @Test
  public void processMessageOfflineTest() {
    Integer accountId = 123;
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(accountId);
    paymentDto.setPaymentType(PaymentType.OFFLINE);
    paymentDto.setAmount(new BigDecimal(50));
    Payment payment = new Payment();
    Account account = new Account();
    Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
    Mockito.when(paymentMapper.dtoToPayment(paymentDto)).thenReturn(payment);

    kafkaService.processMessage(paymentDto);

    Mockito.verify(paymentRepository).save(payment);
    Assertions.assertNotNull(account.getLastPaymentDate());
    Assertions.assertNotNull(payment.getCreatedOn());
    Assertions.assertEquals(account, payment.getAccount());
  }

  @Test
  public void processMessageOnlineValidTest() {
    Integer accountId = 123;
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(accountId);
    paymentDto.setPaymentType(PaymentType.ONLINE);
    paymentDto.setAmount(new BigDecimal(50));
    Payment payment = new Payment();
    Account account = new Account();
    Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
    Mockito.when(restService.isValidPayment(paymentDto)).thenReturn(true);
    Mockito.when(paymentMapper.dtoToPayment(paymentDto)).thenReturn(payment);

    kafkaService.processMessage(paymentDto);

    Mockito.verify(paymentRepository).save(payment);
    Assertions.assertNotNull(account.getLastPaymentDate());
    Assertions.assertNotNull(payment.getCreatedOn());
    Assertions.assertEquals(account, payment.getAccount());
  }

  @Test
  public void processMessageOnlineNotValidTest() {
    Integer accountId = 123;
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(accountId);
    paymentDto.setPaymentType(PaymentType.ONLINE);
    paymentDto.setAmount(new BigDecimal(50));
    Payment payment = new Payment();
    Account account = new Account();
    Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
    Mockito.when(restService.isValidPayment(paymentDto)).thenReturn(false);

    kafkaService.processMessage(paymentDto);

    Mockito.verify(paymentRepository, Mockito.never()).save(payment);
  }

  @Test
  public void processMessageMissingAccountTest() {
    Integer accountId = 123;
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(accountId);
    paymentDto.setPaymentType(PaymentType.ONLINE);
    paymentDto.setAmount(new BigDecimal(50));
    Payment payment = new Payment();
    Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

    kafkaService.processMessage(paymentDto);

    Mockito.verify(paymentRepository, Mockito.never()).save(payment);
    Mockito.verify(restService).logError(Mockito.eq(paymentDto.getPaymentId()),
        Mockito.eq(ErrorType.OTHER), Mockito.anyString());
  }
}
