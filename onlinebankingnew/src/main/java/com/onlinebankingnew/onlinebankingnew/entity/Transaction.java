package com.onlinebankingnew.onlinebankingnew.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "Transaction amount must not be null")
	private Double amount;
	@NotNull(message = "Transaction type must not be null")
	@Enumerated(EnumType.STRING)
	private Transactiontype type;
//	@NotNull(message = "Transaction date must not be null")
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDateTime timestamp;

	@ManyToOne
	@JsonIgnore

	private Account account;

}
