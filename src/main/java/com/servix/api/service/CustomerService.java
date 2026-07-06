package com.servix.api.service;

import com.servix.api.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private final CustomerRepository dao;

    public CustomerService(CustomerRepository dao) {
        this.dao = dao;
    }

    public List<Map<String, Object>> getBookings(int customerId, int pageNumber, int pageSize) {
        Pagination pageInfo = Pagination.of(pageNumber, pageSize);
        return dao.getBookings(customerId, pageInfo.pageSize(), pageInfo.rowOffset());
    }

    public List<Map<String, Object>> getBookingItems(int bookingId) {
        return dao.getBookingItems(bookingId);
    }

    public List<Map<String, Object>> getSpendingSummary(int customerId) {
        return dao.getSpendingSummary(customerId);
    }

    public List<Map<String, Object>> getAddresses(int customerId) {
        return dao.getAddresses(customerId);
    }
}
