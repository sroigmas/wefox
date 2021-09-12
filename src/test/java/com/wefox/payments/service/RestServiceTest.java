package com.wefox.payments.service;

import com.wefox.payments.config.RestConfiguration;
import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.util.enums.ErrorType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class RestServiceTest {

  private static final String GATEWAY_URL = "http://localhost:9000/payment";
  private static final String LOG_URL = "http://localhost:9000/log";

  @Mock
  private RestConfiguration restConfiguration;

  @Mock
  private RestTemplate restTemplate;

  private RestService restService;

  @BeforeEach
  public void init() {
    restService = Mockito.spy(new RestService(restConfiguration, restTemplate));
    Mockito.when(restConfiguration.getGatewayUrl()).thenReturn(GATEWAY_URL);
  }

  @Test
  public void isValidPaymentTrueTest() {
    ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
    Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
        Mockito.any(), Mockito.eq(Void.class))).thenReturn(response);
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(123);

    boolean result = restService.isValidPayment(paymentDto);

    Assertions.assertTrue(result);
  }

  @Test
  public void isValidPaymentFalseTest() {
    ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
        Mockito.any(), Mockito.eq(Void.class))).thenReturn(response);
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(123);

    boolean result = restService.isValidPayment(paymentDto);

    Assertions.assertFalse(result);
  }

  @Test
  public void isValidPaymentExceptionTest() {
    Mockito.when(restConfiguration.getLogUrl()).thenReturn(LOG_URL);
    Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
        Mockito.any(), Mockito.eq(Void.class))).thenThrow(RestClientException.class);
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setPaymentId("123");
    paymentDto.setAccountId(123);

    boolean result = restService.isValidPayment(paymentDto);

    Assertions.assertFalse(result);
    Mockito.verify(restService).logError("123", ErrorType.NETWORK, null);
  }
}
