package com.wefox.payments.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
@Data
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "account_id")
  private Integer accountId;

  private String name;

  @Column(nullable = false)
  private String email;

  private LocalDate birthdate;

  @Column(name = "last_payment_date")
  private LocalDateTime lastPaymentDate;

  @Column(name = "created_on")
  private LocalDateTime createdOn;
}
