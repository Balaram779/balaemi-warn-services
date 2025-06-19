package com.smartbalaram.emi.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;

public class RiskLevelUtilsTest {

	@BeforeAll
	static void setup() {
	    RiskLevelUtils.setWeights(0.6, 2.0, 0.1);
	    RiskLevelUtils.setThresholds(30, 60);
	}


    @Test
    void testDetermineRiskLevel_Low() {
        double emiPercent = 20.0;
        int missedEmi = 0;
        int tenure = 6;

        String risk = RiskLevelUtils.determineRiskLevel(emiPercent, missedEmi, tenure);
        assertEquals("LOW", risk);
    }

    @Test
    void testDetermineRiskLevel_Moderate() {
        double emiPercent = 50.0;
        int missedEmi = 2;
        int tenure = 18;

        String risk = RiskLevelUtils.determineRiskLevel(emiPercent, missedEmi, tenure);
        assertEquals("MODERATE", risk);
    }


    @Test
    void testDetermineRiskLevel_High() {
        double emiPercent = 80.0;
        int missedEmi = 5;
        int tenure = 36;

        String risk = RiskLevelUtils.determineRiskLevel(emiPercent, missedEmi, tenure);
        assertEquals("HIGH", risk);
    }


    @Test
    void testGetWarningMessage() {
        assertEquals("✅ Your EMI is within safe limits.", RiskLevelUtils.getWarningMessage("LOW"));
        assertEquals("⚠️ Moderate risk. Please review EMI obligations.", RiskLevelUtils.getWarningMessage("MODERATE"));
        assertEquals("🚨 High EMI burden detected. Immediate action advised.", RiskLevelUtils.getWarningMessage("HIGH"));
    }


    @AfterAll
    static void cleanup() {
        RiskLevelUtils.setWeights(0.6, 2.0, 0.1);
        RiskLevelUtils.setThresholds(30, 60);
    }

}
