package com.servix.api.controller;

import com.servix.api.service.ProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/providers")
@Tag(name = "3. Provider", description = "Jobs, earnings, rating comparison")
public class ProviderController {

    private final ProviderService logic;

    public ProviderController(ProviderService logic) {
        this.logic = logic;
    }

    @GetMapping("/{id}/pending")
    @Operation(summary = "Pending jobs",
            description = "All pending jobs for a provider with customer name and service location. Supports pagination.")
    public ResponseEntity<List<Map<String, Object>>> getPendingJobs(
            @Parameter(description = "Provider ID") @PathVariable("id") int providerId,
            @Parameter(description = "Page number (1-indexed)") @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Parameter(description = "Results per page") @RequestParam(value = "limit", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(logic.getPendingJobs(providerId, pageNumber, pageSize));
    }

    @GetMapping("/{id}/completed")
    @Operation(summary = "Completed jobs",
            description = "All completed jobs with payment status. Uses LEFT JOIN for optional payments. Supports pagination.")
    public ResponseEntity<List<Map<String, Object>>> getCompletedJobs(
            @Parameter(description = "Provider ID") @PathVariable("id") int providerId,
            @Parameter(description = "Page number (1-indexed)") @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Parameter(description = "Results per page") @RequestParam(value = "limit", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(logic.getCompletedJobs(providerId, pageNumber, pageSize));
    }

    @GetMapping("/{id}/earnings")
    @Operation(summary = "Earnings summary",
            description = "Total earnings grouped by payment method. Uses SUM, COUNT, GROUP BY.")
    public ResponseEntity<List<Map<String, Object>>> getEarnings(
            @Parameter(description = "Provider ID") @PathVariable("id") int providerId) {
        return ResponseEntity.ok(logic.getEarnings(providerId));
    }

    @GetMapping("/{id}/rating")
    @Operation(summary = "Rating vs city average",
            description = "Compares provider's rating against city average using a DERIVED TABLE (subquery in FROM clause). Uses CASE for comparison label.")
    public ResponseEntity<List<Map<String, Object>>> getRatingComparison(
            @Parameter(description = "Provider ID") @PathVariable("id") int providerId) {
        return ResponseEntity.ok(logic.getRatingComparison(providerId));
    }
}
