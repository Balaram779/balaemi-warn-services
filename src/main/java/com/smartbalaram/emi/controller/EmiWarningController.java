package com.smartbalaram.emi.controller;

import com.smartbalaram.emi.model.EmiRequest;
import com.smartbalaram.emi.model.EmiResponse;
import com.smartbalaram.emi.service.EmiWarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for EMI warning service. Handles requests related to EMI
 * evaluation, risk level, cap calculation, etc.
 */
@RestController
@RequestMapping("/api/emi") // Base URL for all endpoints in this controller
@RequiredArgsConstructor // Auto-generates constructor for final fields (emiWarningService)
@CrossOrigin(origins = "*") // ✅ Allow local React access
public class EmiWarningController {

	private final EmiWarningService emiWarningService;

	/**
	 * Endpoint to evaluate EMI risk based on EMI amount and income.
	 * 
	 * @param request Incoming request with EMI details.
	 * @return EMI evaluation response containing risk level and message.
	 */
	@PostMapping("/warn")
	public ResponseEntity<EmiResponse> getEmiWarning(@RequestBody EmiRequest request) {
		return ResponseEntity.ok(emiWarningService.evaluateEmi(request));
	}

	/**
	 * Endpoint to get recommended EMI cap for a given income. Example logic: 40% of
	 * monthly income.
	 * 
	 * @param monthlyIncome Monthly income of the user.
	 * @return Recommended maximum EMI value.
	 */
	@GetMapping("/cap/{monthlyIncome}")
	public ResponseEntity<Double> getRecommendedEmiCap(@PathVariable("monthlyIncome") double monthlyIncome) {
		return ResponseEntity.ok(emiWarningService.getRecommendedCap(monthlyIncome));
	}

	/**
	 * Endpoint to get risk level for a given EMI-to-income percentage.
	 * 
	 * @param percentage EMI percentage of income.
	 * @return Risk level (LOW, MODERATE, HIGH).
	 */
	@GetMapping("/risk/{percentage}")
	public ResponseEntity<String> getRiskLevel(@PathVariable("percentage") double percentage) {
		return ResponseEntity.ok(emiWarningService.getRiskLevel(percentage));
	}

	/**
	 * Endpoint to retrieve all configured EMI risk threshold messages.
	 * 
	 * @return List of threshold messages.
	 */
	@GetMapping("/thresholds")
	public ResponseEntity<List<String>> getThresholdDefinitions() {
		return ResponseEntity.ok(emiWarningService.getThresholdDetails());
	}

	/**
	 * Health check endpoint to verify service is running.
	 * 
	 * @return Success message if service is up.
	 */
	@GetMapping("/ping")
	public String ping() {
		return "✅ EMI service is up!";
	}
}
