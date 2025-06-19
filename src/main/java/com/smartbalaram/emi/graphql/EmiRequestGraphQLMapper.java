package com.smartbalaram.emi.graphql;

import com.smartbalaram.emi.model.EmiRequest;
import com.smartbalaram.emi.util.RiskLevelUtils;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

/**
 * GraphQL schema mapping for computed EMIRequest fields.
 */
@Controller
public class EmiRequestGraphQLMapper {

    @SchemaMapping(typeName = "EmiRequest", field = "emiPercentage")
    public double getEmiPercentage(EmiRequest request) {
        if (request.getMonthlyIncome() == 0) return 0;
        return (request.getTotalEmiAmount() * 100.0) / request.getMonthlyIncome();
    }

    @SchemaMapping(typeName = "EmiRequest", field = "riskLevel")
    public String getRiskLevel(EmiRequest request) {
        double percentage = (request.getTotalEmiAmount() * 100.0) / request.getMonthlyIncome();
        return RiskLevelUtils.determineRiskLevel(percentage, 0, 0);
    }

    @SchemaMapping(typeName = "EmiRequest", field = "warningMessage")
    public String getWarningMessage(EmiRequest request) {
        double percentage = (request.getTotalEmiAmount() * 100.0) / request.getMonthlyIncome();
        String risk = RiskLevelUtils.determineRiskLevel(percentage, 0, 0);
        return RiskLevelUtils.getWarningMessage(risk);
    }
}
