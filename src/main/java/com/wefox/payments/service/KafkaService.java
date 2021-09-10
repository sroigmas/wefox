package com.wefox.payments.service;

public interface KafkaService<T> {
  void processMessage(T message);
}
