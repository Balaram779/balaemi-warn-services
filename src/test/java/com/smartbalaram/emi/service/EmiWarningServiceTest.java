package com.smartbalaram.emi.service;

import com.smartbalaram.emi.model.EmiRequest;
import com.smartbalaram.emi.model.EmiResponse;
import com.smartbalaram.emi.repository.EmiWarningRepository;
import com.smartbalaram.emi.util.RiskLevelUtils;
import com.smartbalaram.emi.kafka.KafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.smartbalaram.emi.util.AppConstants.*;



class EmiWarningServiceTest {

    @Mock
    private KafkaProducerService kafkaProducerService;

    @Mock
    private EmiWarningRepository repository;

    @InjectMocks
    private EmiWarningService emiWarningService;

    private EmiRequest request;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // ✅ Set realistic AI scoring config for test
        RiskLevelUtils.setWeights(0.5, 1.0, 0.2);  // (emi%, missedEMI, tenure)
        RiskLevelUtils.setThresholds(20.0, 40.0);  // LOW ≤ 20, MODERATE ≤ 40, else HIGH

        request = new EmiRequest();
        request.setUserId("user123");
        request.setMonthlyIncome(30000.0);
        request.setTotalEmiAmount(25000.0);
        request.setMissedEmiCount(10);
        request.setLoanTenureMonths(36);
    }


    @Test
    void testEvaluateEmi_HighRisk() {
        EmiResponse response = emiWarningService.evaluateEmi(request);
        assertEquals("HIGH", response.getRiskLevel());
    }


    @Test
    void testEvaluateEmi_ModerateRisk() {
        request.setMonthlyIncome(50000.0);
        request.setTotalEmiAmount(20000.0); // 40% EMI
        request.setMissedEmiCount(2);
        request.setLoanTenureMonths(12);

        EmiResponse response = emiWarningService.evaluateEmi(request);
        assertEquals(RISK_MODERATE, response.getRiskLevel()); // ✅ not "MEDIUM"
    }


    @Test
    void testEvaluateEmi_LowRisk() {
        request.setMonthlyIncome(100000.0);
        request.setTotalEmiAmount(10000.0);
        request.setMissedEmiCount(0);
        request.setLoanTenureMonths(6);

        EmiResponse response = emiWarningService.evaluateEmi(request);
        assertEquals("LOW", response.getRiskLevel());
    }

    @Test
    void testGetRecommendedCap() {
        Double cap = emiWarningService.getRecommendedEmiCap("user123", 60000.0);
        assertEquals(24000.0, cap); // 60000 * 0.4
    }
}
