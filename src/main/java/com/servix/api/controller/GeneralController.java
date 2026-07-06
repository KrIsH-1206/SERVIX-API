package com.servix.api.controller;

import com.servix.api.service.GeneralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/general")
@Tag(name = "1. General", description = "Browse services, search providers, check availability")
public class GeneralController {

    private final GeneralService logic;

    public GeneralController(GeneralService logic) {
        this.logic = logic;
    }

    @GetMapping("/services")
    @Operation(summary = "Browse all services", description = "List all services grouped by category with base prices. Supports pagination.")
    public ResponseEntity<List<Map<String, Object>>> getAllServices(
            @Parameter(description = "Page number (1-indexed)") @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Parameter(description = "Results per page") @RequestParam(value = "limit", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(logic.getAllServices(pageNumber, pageSize));
    }

    @GetMapping("/services/search")
    @Operation(summary = "Search providers by service",
            description = "Find active providers offering a service matching the keyword. Uses 5-table JOIN with ILIKE. Supports pagination.")
    public ResponseEntity<List<Map<String, Object>>> searchProviders(
            @Parameter(description = "Service name keyword (e.g. cleaning, fan, painting)")
            @RequestParam(value = "q") String searchTerm,
            @Parameter(description = "Page number (1-indexed)") @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Parameter(description = "Results per page") @RequestParam(value = "limit", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(logic.searchProviders(searchTerm, pageNumber, pageSize));
    }

    @GetMapping("/providers/top")
    @Operation(summary = "Top rated providers",
            description = "Returns the highest-rated verified providers across all cities. Defaults to top 5.")
    public ResponseEntity<List<Map<String, Object>>> getTopProviders(
            @Parameter(description = "Number of top providers to return") @RequestParam(value = "top", defaultValue = "5") int topCount) {
        return ResponseEntity.ok(logic.getTopProviders(topCount));
    }
}
