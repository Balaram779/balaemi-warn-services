package com.smartbalaram.emi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "risk")
public class RiskConfig {

    private double weightEmiPercent;
    private double weightMissedEmi;
    private double weightTenure;
    private double scoreThresholdLow;
    private double scoreThresholdMedium;
}
