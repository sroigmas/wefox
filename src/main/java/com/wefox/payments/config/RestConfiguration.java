package com.wefox.payments.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Data
public class RestConfiguration {

  @Value("${rest.gateway.url}")
  private String gatewayUrl;

  @Value("${rest.log.url}")
  private String logUrl;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
