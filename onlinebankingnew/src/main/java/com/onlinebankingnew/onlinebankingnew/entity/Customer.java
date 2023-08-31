package com.onlinebankingnew.onlinebankingnew.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customerId;

	@javax.validation.constraints.NotBlank(message = "Please add customer name")
	@Column(unique = true)
	private String customerName;

	@Size(min = 10, max=10,message = "Please provide phone number or Invalid Number ")
	@Column(unique = true)
	private String phoneNo;
	
	
	@Column(unique = true)
	@Email(message = "invalid Email")
	private String customerEmail;

	@javax.validation.constraints.NotBlank(message = "Please add password")
	private String customerPassword;
	
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate customerDOB;
//	@NotNull

	@Size(min=5,max=20)
	private String customerAddress;
	
	@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Account> account;

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", phoneNo=" + phoneNo
				+ ", customerEmail=" + customerEmail + ", customerPassword=" + customerPassword + ", customerDOB="
				+ customerDOB + ", customerAddress=" + customerAddress + "]";
	}
	

	
	
	
}
