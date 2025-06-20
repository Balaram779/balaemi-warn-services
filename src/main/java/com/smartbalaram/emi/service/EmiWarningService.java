package com.smartbalaram.emi.service;

import com.smartbalaram.emi.kafka.KafkaProducerService;
import com.smartbalaram.emi.model.EmiRequest;
import com.smartbalaram.emi.model.EmiResponse;
import com.smartbalaram.emi.repository.EmiWarningRepository;
import com.smartbalaram.emi.util.RiskLevelUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.smartbalaram.emi.util.AppConstants.*;

@Service
@RequiredArgsConstructor
public class EmiWarningService {

//    private final KafkaProducerService kafkaProducerService;
    private final EmiWarningRepository emiWarningRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmiWarningService.class);

    /**
     * ✅ Core logic to evaluate EMI risk using AI-like scoring
     */
    public EmiResponse evaluateEmi(EmiRequest request) {
        logger.info("🔍 Evaluating EMI for user: {}", request.getUserId());

        double emiPercentage = (request.getTotalEmiAmount() / request.getMonthlyIncome()) * 100;

        String riskLevel = RiskLevelUtils.determineRiskLevel(
                emiPercentage,
                request.getMissedEmiCount(),
                request.getLoanTenureMonths()
        );

        String warning = RiskLevelUtils.getWarningMessage(riskLevel);

        double suggestedMaxEmi = Math.round(request.getMonthlyIncome() * RECOMMENDED_EMI_CAP_RATIO);

        emiWarningRepository.save(request);
       // kafkaProducerService.sendEmiWarning(request.getUserId(),
               // buildResponse(request, emiPercentage, riskLevel, warning, suggestedMaxEmi));

        return buildResponse(request, emiPercentage, riskLevel, warning, suggestedMaxEmi);
    }

    public EmiResponse saveEmi(EmiRequest request) {
        logger.info("💾 Saving EMI for user: {}", request.getUserId());

        request.setWarning(request.getTotalEmiAmount() > 0.5 * request.getMonthlyIncome());
        emiWarningRepository.save(request);

        return EmiResponse.builder()
                .userId(request.getUserId())
                .monthlyIncome(request.getMonthlyIncome())
                .totalEmiAmount(request.getTotalEmiAmount())
                .warning(request.isWarning())
                .build();
    }

    public List<EmiRequest> getAllEmiRecords() {
        logger.info("📥 Fetching all EMI records");
        return emiWarningRepository.findAll();
    }

    public EmiRequest getEmiById(String userId) {
        logger.info("🔍 Fetching EMI by userId: {}", userId);
        return emiWarningRepository.findById(userId)
        		.orElse(null); // ✅ avoid crashing
    }


    public void deleteEmiById(String userId) {
        logger.warn("🗑️ Deleting EMI record for userId: {}", userId);
        emiWarningRepository.deleteById(userId);
    }

    public Double getRecommendedEmiCap(String userId, double monthlyIncome) {
        return Double.valueOf(Math.round(monthlyIncome * 0.4));
    }


    // ➤ Method #2: Dynamic percentage (for advanced configs)
    public double getRecommendedCap(double income) {
        return income * 0.5; // fallback logic (can be changed to config-based)
    }

    public String getRiskLevel(double percentage) {
        return RiskLevelUtils.determineRiskLevel(percentage, 0, 0);
    }

    public List<String> getThresholdDetails() {
        return List.of(
            RISK_LOW + ": score ≤ " + RiskLevelUtils.getScoreThresholdLow(),
            RISK_MODERATE + ": score ≤ " + RiskLevelUtils.getScoreThresholdMedium(),
            RISK_HIGH + ": score > " + RiskLevelUtils.getScoreThresholdMedium()
        );
    }

    private EmiResponse buildResponse(EmiRequest request, double emiPercentage, String riskLevel,
                                      String warningMessage, double suggestedMaxEmi) {
        return EmiResponse.builder()
                .userId(request.getUserId())
                .monthlyIncome(request.getMonthlyIncome())
                .totalEmiAmount(request.getTotalEmiAmount())
                .warning(request.isWarning())
                .emiPercentage(Math.round(emiPercentage * 100.0) / 100.0)
                .riskLevel(riskLevel)
                .warningMessage(warningMessage)
                .suggestedMaxEmi(suggestedMaxEmi)
                .build();
    }
}
