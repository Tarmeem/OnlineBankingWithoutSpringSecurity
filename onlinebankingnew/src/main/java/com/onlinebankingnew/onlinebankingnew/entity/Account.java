package com.onlinebankingnew.onlinebankingnew.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity 
@AllArgsConstructor 
@NoArgsConstructor
@Builder
@ToString(exclude="transaction")

public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long accountId;
	
	@NotBlank(message = "Must not be blank")
	private String accountType;
	
	@NotBlank(message = "Must not be blank")
	private String IFSCcode;
	@NotNull(message = "Account balance must not be null")
	@DecimalMin(value = "0.0", inclusive = false, message = "Account balance must be greater than 0")
	private Double accountBalance;
	
	@NotBlank(message = "Must not be blank")
	private String accountNumber;
	
	@ManyToOne
	@JsonIgnore
	//@JsonInclude(JsonInclude.Include.NON_NULL)
//	@JoinColumn(name="")
	private Customer customer;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account",cascade=CascadeType.ALL)
	private List<Transaction> transaction;

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountType=" + accountType + ", IFSCcode=" + IFSCcode
				+ ", accountBalance=" + accountBalance + ", accountNumber=" + accountNumber + "]";
	}


}


