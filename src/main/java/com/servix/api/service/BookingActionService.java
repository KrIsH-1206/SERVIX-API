package com.servix.api.service;

import com.servix.api.repository.BookingActionRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookingActionService {

    private final BookingActionRepository dao;

    public BookingActionService(BookingActionRepository dao) {
        this.dao = dao;
    }

    public Map<String, Object> cancelBooking(int bookingId, String cancellationReason) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Booking id must be a positive integer");
        }
        String normalizedReason = (cancellationReason == null || cancellationReason.isBlank())
                ? "Cancelled via API"
                : cancellationReason.trim();
        return dao.cancelBooking(bookingId, normalizedReason);
    }

    public Map<String, Object> confirmBooking(int bookingId) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Booking id must be a positive integer");
        }
        return dao.confirmBooking(bookingId);
    }
}
