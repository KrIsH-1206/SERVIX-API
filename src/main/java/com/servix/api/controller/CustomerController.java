package com.servix.api.controller;

import com.servix.api.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "2. Customer", description = "Bookings, spending summary, saved addresses")
public class CustomerController {

    private final CustomerService logic;

    public CustomerController(CustomerService logic) {
        this.logic = logic;
    }

    @GetMapping("/{id}/bookings")
    @Operation(summary = "My bookings", description = "All bookings for a customer ordered by date descending. Supports pagination.")
    public ResponseEntity<List<Map<String, Object>>> getBookings(
            @Parameter(description = "Customer ID") @PathVariable("id") int customerId,
            @Parameter(description = "Page number (1-indexed)") @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Parameter(description = "Results per page") @RequestParam(value = "limit", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(logic.getBookings(customerId, pageNumber, pageSize));
    }

    @GetMapping("/{customerId}/bookings/{bookingId}/items")
    @Operation(summary = "Booking items", description = "Itemized breakdown of a booking with line totals. Uses LEFT JOIN for optional variants.")
    public ResponseEntity<List<Map<String, Object>>> getBookingItems(
            @PathVariable("customerId") int customerId,
            @Parameter(description = "Booking ID") @PathVariable("bookingId") int bookingId) {
        return ResponseEntity.ok(logic.getBookingItems(bookingId));
    }

    @GetMapping("/{id}/spending")
    @Operation(summary = "Spending summary",
            description = "Aggregate spending stats: total bookings, completed/cancelled/pending counts, total spent, avg per booking. Uses CASE, SUM, COUNT, GROUP BY.")
    public ResponseEntity<List<Map<String, Object>>> getSpendingSummary(
            @Parameter(description = "Customer ID") @PathVariable("id") int customerId) {
        return ResponseEntity.ok(logic.getSpendingSummary(customerId));
    }

    @GetMapping("/{id}/addresses")
    @Operation(summary = "Saved addresses",
            description = "Customer's saved addresses via 4-table JOIN: customer_addresses → locations → areas → cities. BCNF-compliant schema design.")
    public ResponseEntity<List<Map<String, Object>>> getAddresses(
            @Parameter(description = "Customer ID") @PathVariable("id") int customerId) {
        return ResponseEntity.ok(logic.getAddresses(customerId));
    }
}
