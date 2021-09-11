package com.wefox.payments.util.mapper;

import com.wefox.payments.dto.PaymentDto;
import com.wefox.payments.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

  Payment dtoToPayment(PaymentDto paymentDto);
  PaymentDto paymentToDto(Payment payment);
}
