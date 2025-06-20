package com.smartbalaram.emi.graphql;

import com.smartbalaram.emi.model.EmiRequest;
import com.smartbalaram.emi.model.EmiResponse;
import com.smartbalaram.emi.service.EmiWarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * GraphQL controller for EMI operations.
 * Supports querying and mutating EMI data using GraphQL schema.
 */
@Controller
@RequiredArgsConstructor // Injects final emiWarningService automatically
//@CrossOrigin(origins = "*") // ✅ Allow local React access
public class EmiGraphQLController {

    private final EmiWarningService emiWarningService;

    
    /**
     * GraphQL Mutation: Evaluate EMI risk based on input request.
     * @param input EMIRequest object containing EMI and income.
     * @return EMIResponse with risk info.
     */

    @MutationMapping
    public EmiResponse evaluateEmi(@Argument("input") EmiRequest input) {
        return emiWarningService.evaluateEmi(input);
    }
    
    /**
     * GraphQL Query: Get the risk level based on EMI-to-income percentage.
     * @param percentage EMI percentage of income.
     * @return Risk level string.
     */
    @QueryMapping
    public String getRiskLevel(@Argument("percentage") double percentage) {
        return emiWarningService.getRiskLevel(percentage);
    }
   
    /**
     * GraphQL Query: Get all EMI records stored in the database.
     * @return List of EMIRequest objects.
     */
    @QueryMapping
    public List<EmiRequest> allEmiRecords() {
        return emiWarningService.getAllEmiRecords();
    }
    
    /**
     * GraphQL Query: Get EMI record by user ID.
     * @param userId The unique user ID.
     * @return Corresponding EMIRequest record.
     */
    @QueryMapping
    public EmiRequest getEmiById(@Argument("userId") String userId) {
        return emiWarningService.getEmiById(userId);
    }


    /**
     * GraphQL Query: Get all EMI threshold definitions (for LOW, MODERATE, HIGH).
     * @return List of string thresholds.
     */
    @QueryMapping
    public List<String> getThresholdDetails() {
        return emiWarningService.getThresholdDetails();
    }


    /**
     * GraphQL Mutation: Save EMI record to the database.
     * @param input EMIRequest object to save.
     * @return Saved EMIResponse.
     */
    @MutationMapping
    public EmiResponse saveEmi(@Argument("input") EmiRequest input) {
        return emiWarningService.saveEmi(input);
    }

    /**
     * GraphQL Mutation: Delete EMI record by user ID.
     * @param userId The ID to delete.
     * @return true if deletion succeeded.
     */
    @MutationMapping
    public boolean deleteEmiById(@Argument("userId") String userId) {
        emiWarningService.deleteEmiById(userId);
        return true;
    }
    
    @QueryMapping
    public Double getRecommendedCap(@Argument("monthlyIncome") double monthlyIncome) {
        return emiWarningService.getRecommendedCap(monthlyIncome);
    }

    
}

/*
  @QueryMapping	-->Used for queries (read-only data fetching)
  @MutationMapping-->	Used for mutations (data-changing operations)Why you need @MutationMapping
You use @MutationMapping when your method:
Changes server-side data
Saves, updates, deletes, or triggers any write operation */
