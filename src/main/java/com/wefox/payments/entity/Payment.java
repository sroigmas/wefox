package com.wefox.payments.entity;

import com.wefox.payments.util.enums.PaymentType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
@Data
public class Payment {

  @Id
  @Column(name = "payment_id")
  private String paymentId;

  @ManyToOne(optional = false, cascade = CascadeType.MERGE)
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(name = "payment_type", nullable = false)
  private PaymentType paymentType;

  @Column(name = "credit_card")
  private String creditCard;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(name = "created_on")
  private LocalDateTime createdOn;
}
