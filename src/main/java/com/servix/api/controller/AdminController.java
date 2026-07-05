package com.servix.api.controller;

import com.servix.api.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "4. Admin Analytics", description = "Revenue reports, provider leaderboard, complaint dashboard")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping("/revenue")
    @Operation(summary = "Revenue by category",
            description = "Total revenue and booking count per service category. Uses 5-table JOIN with SUM and GROUP BY.")
    public ResponseEntity<List<Map<String, Object>>> getRevenueByCat() {
        return ResponseEntity.ok(service.getRevenueByCategory());
    }

    @GetMapping("/leaderboard")
    @Operation(summary = "Provider leaderboard",
            description = "Ranks providers by rating and completed jobs. Use ?fast=true to read from a pre-computed Materialized View for O(1) performance.")
    public ResponseEntity<List<Map<String, Object>>> getLeaderboard(
            @Parameter(description = "Use materialized view for faster response") @RequestParam(defaultValue = "false") boolean fast) {
        return ResponseEntity.ok(service.getLeaderboard(fast));
    }

    @GetMapping("/complaints")
    @Operation(summary = "Complaint dashboard",
            description = "Complaint status summary with affected cities using STRING_AGG aggregate across a 5-table JOIN (complaints → bookings → locations → areas → cities).")
    public ResponseEntity<List<Map<String, Object>>> getComplaintDashboard() {
        return ResponseEntity.ok(service.getComplaintDashboard());
    }

    @GetMapping("/city-revenue")
    @Operation(summary = "City revenue summary",
            description = "Returns pre-computed revenue, booking count, and unique customers per city from a Materialized View.")
    public ResponseEntity<List<Map<String, Object>>> getCityRevenue() {
        return ResponseEntity.ok(service.getCityRevenue());
    }

    @PostMapping("/refresh-views")
    @Operation(summary = "Refresh materialized views",
            description = "Rebuilds mv_provider_leaderboard and mv_city_revenue_summary with the latest data from the base tables.")
    public ResponseEntity<Map<String, Object>> refreshViews() {
        return ResponseEntity.ok(service.refreshViews());
    }
}
