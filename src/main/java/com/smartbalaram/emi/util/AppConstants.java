
package com.smartbalaram.emi.util;

public class AppConstants {

	// AI Scoring Risk Levels
	public static final String RISK_LOW = "LOW";
	public static final String RISK_MODERATE = "MODERATE";
	public static final String RISK_HIGH = "HIGH";

	// Risk Messages
	public static final String MESSAGE_SAFE = "✅ Your EMI is within safe limits.";
	public static final String MESSAGE_CAUTION = "⚠️ Moderate risk. Please review EMI obligations.";
	public static final String MESSAGE_RISK = "🚨 High EMI burden detected. Immediate action advised.";
	public static final String MESSAGE_INVALID = "❌ Invalid risk level.";

	// Recommended EMI cap ratio
	public static final double RECOMMENDED_EMI_CAP_RATIO = 0.3;
}
