package com.servix.api.service;

import com.servix.api.repository.GeneralRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Business logic for browsing services and searching providers.
 * Owns pagination normalization so controllers stay thin and
 * repositories stay focused on pure SQL access.
 */
@Service
public class GeneralService {

    private final GeneralRepository dao;

    public GeneralService(GeneralRepository dao) {
        this.dao = dao;
    }

    public List<Map<String, Object>> getAllServices(int pageNumber, int pageSize) {
        Pagination pageInfo = Pagination.of(pageNumber, pageSize);
        return dao.getAllServices(pageInfo.pageSize(), pageInfo.rowOffset());
    }

    public List<Map<String, Object>> searchProviders(String query, int pageNumber, int pageSize) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Search keyword 'q' must not be empty");
        }
        Pagination pageInfo = Pagination.of(pageNumber, pageSize);
        return dao.searchProviders(query.trim(), pageInfo.pageSize(), pageInfo.rowOffset());
    }

    public List<Map<String, Object>> getTopProviders(int topCount) {
        int normalizedTopCount = Math.min(Math.max(topCount, 1), 50);
        return dao.getTopProviders(normalizedTopCount);
    }
}
