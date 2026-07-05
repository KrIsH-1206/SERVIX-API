package com.servix.api.service;

import com.servix.api.repository.BookingActionRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookingActionService {

    private final BookingActionRepository repository;

    public BookingActionService(BookingActionRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> cancelBooking(int bookingId, String reason) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Booking id must be a positive integer");
        }
        String safeReason = (reason == null || reason.isBlank()) ? "Cancelled via API" : reason.trim();
        return repository.cancelBooking(bookingId, safeReason);
    }

    public Map<String, Object> confirmBooking(int bookingId) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Booking id must be a positive integer");
        }
        return repository.confirmBooking(bookingId);
    }
}
