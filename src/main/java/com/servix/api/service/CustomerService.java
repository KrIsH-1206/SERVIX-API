package com.servix.api.service;

import com.servix.api.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Map<String, Object>> getBookings(int customerId, int page, int limit) {
        Pagination p = Pagination.of(page, limit);
        return repository.getBookings(customerId, p.limit(), p.offset());
    }

    public List<Map<String, Object>> getBookingItems(int bookingId) {
        return repository.getBookingItems(bookingId);
    }

    public List<Map<String, Object>> getPayments(int customerId, int page, int limit) {
        Pagination p = Pagination.of(page, limit);
        return repository.getPayments(customerId, p.limit(), p.offset());
    }

    public List<Map<String, Object>> getSpendingSummary(int customerId) {
        return repository.getSpendingSummary(customerId);
    }

    public List<Map<String, Object>> getAddresses(int customerId) {
        return repository.getAddresses(customerId);
    }
}
