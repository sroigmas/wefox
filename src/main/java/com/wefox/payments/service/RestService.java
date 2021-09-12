package com.wefox.payments.service;

import com.wefox.payments.config.RestConfiguration;
import com.wefox.payments.dto.ErrorDto;
import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.util.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@AllArgsConstructor
public class RestService {

  private RestConfiguration restConfiguration;
  private RestTemplate restTemplate;

  public boolean isValidPayment(PaymentDto paymentDto) {
    HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto);

    try {
      ResponseEntity<Void> response = restTemplate.exchange(restConfiguration.getGatewayUrl(),
          HttpMethod.POST, request, Void.class);
      log.debug("Validated payment with id '{}' for account '{}' and status '{}'",
          paymentDto.getPaymentId(), paymentDto.getAccountId(), response.getStatusCode().value());
      return response.getStatusCode().is2xxSuccessful();
    } catch (RestClientException e) {
      log.error("Error validating payment with id '{}' for account '{}'",
          paymentDto.getPaymentId(), paymentDto.getAccountId(), e);
      logError(paymentDto.getPaymentId(), ErrorType.NETWORK, e.getMessage());
      return false;
    }
  }

  public void logError(String paymentId, ErrorType type, String description) {
    ErrorDto errorDto = new ErrorDto(paymentId, type, description);
    HttpEntity<ErrorDto> request = new HttpEntity<>(errorDto);

    try {
      restTemplate.exchange(restConfiguration.getLogUrl(), HttpMethod.POST, request, Void.class);
      log.debug("Logged error for payment '{}', type '{}' and description '{}'",
          paymentId, type, description);
    } catch (RestClientException e) {
      log.error("Error logging error for payment '{}', type '{}' and description '{}'",
          paymentId, type, description);
    }
  }
}
