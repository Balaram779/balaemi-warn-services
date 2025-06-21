package com.smartbalaram.emi.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Emi")
public class EmiRequest {
	
	@Indexed(unique = true)
    private String userId;
    private double monthlyIncome;
    private double totalEmiAmount;
    private boolean warning;

    // ✅ Required for AI Risk Scoring Engine
    private int missedEmiCount;       // No. of missed EMIs by user
    private int loanTenureMonths;     // Total loan duration in months
}

// Note: This class represents the request model for EMI calculations and warnings.