package com.smartbalaram.emi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmiResponse {
    private String userId;
    private double monthlyIncome;
    private double totalEmiAmount;
    private boolean warning;

    private double emiPercentage;
    private String riskLevel;
    private String warningMessage;
    private double suggestedMaxEmi;
}
