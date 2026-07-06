package com.servix.api.controller;

import com.servix.api.service.BookingActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "5. Booking Actions", description = "Cancel or confirm bookings using PostgreSQL stored procedures")
public class BookingActionController {

    private final BookingActionService logic;

    public BookingActionController(BookingActionService logic) {
        this.logic = logic;
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel a booking",
            description = "Calls the sp_cancel_booking PostgreSQL stored procedure. Handles status update, cancellation record creation, and conditional refund processing within a database transaction.")
    public ResponseEntity<Map<String, Object>> cancelBooking(
            @Parameter(description = "Booking ID") @PathVariable("id") int bookingId,
            @Parameter(description = "Cancellation reason") @RequestParam(value = "reason", defaultValue = "Cancelled via API") String cancellationReason) {
        return ResponseEntity.ok(logic.cancelBooking(bookingId, cancellationReason));
    }

    @PostMapping("/{id}/confirm")
    @Operation(summary = "Confirm a booking",
            description = "Calls the sp_confirm_booking PostgreSQL stored procedure. Updates booking status and logs the status change.")
    public ResponseEntity<Map<String, Object>> confirmBooking(
            @Parameter(description = "Booking ID") @PathVariable("id") int bookingId) {
        return ResponseEntity.ok(logic.confirmBooking(bookingId));
    }
}
