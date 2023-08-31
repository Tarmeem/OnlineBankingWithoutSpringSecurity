package com.onlinebankingnew.onlinebankingnew.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
@Builder
public class PaymentTransferForm {
    

    private Long sourceAccountId;
    private Long targetAccountId;
    private Double amount;
}
