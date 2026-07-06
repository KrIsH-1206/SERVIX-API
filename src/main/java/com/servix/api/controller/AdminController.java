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

    private final AdminService logic;

    public AdminController(AdminService logic) {
        this.logic = logic;
    }

    @GetMapping("/revenue")
    @Operation(summary = "Revenue by category",
            description = "Total revenue and booking count per service category. Uses 5-table JOIN with SUM and GROUP BY.")
    public ResponseEntity<List<Map<String, Object>>> getRevenueByCat() {
        return ResponseEntity.ok(logic.getRevenueByCategory());
    }

    @GetMapping("/leaderboard")
    @Operation(summary = "Provider leaderboard",
            description = "Ranks providers by rating and completed jobs. Use ?fast=true to read from a pre-computed Materialized View for O(1) performance.")
    public ResponseEntity<List<Map<String, Object>>> getLeaderboard(
            @Parameter(description = "Use materialized view for faster response") @RequestParam(value = "fast", defaultValue = "false") boolean useFastRead) {
        return ResponseEntity.ok(logic.getLeaderboard(useFastRead));
    }

    @GetMapping("/complaints")
    @Operation(summary = "Complaint dashboard",
            description = "Complaint status summary with affected cities using STRING_AGG aggregate across a 5-table JOIN (complaints → bookings → locations → areas → cities).")
    public ResponseEntity<List<Map<String, Object>>> getComplaintDashboard() {
        return ResponseEntity.ok(logic.getComplaintDashboard());
    }

    @PostMapping("/refresh-views")
    @Operation(summary = "Refresh materialized view",
            description = "Rebuilds mv_provider_leaderboard with the latest data from the base tables.")
    public ResponseEntity<Map<String, Object>> refreshViews() {
        return ResponseEntity.ok(logic.refreshViews());
    }
}
