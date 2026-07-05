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

    private final GeneralRepository repository;

    public GeneralService(GeneralRepository repository) {
        this.repository = repository;
    }

    public List<Map<String, Object>> getAllServices(int page, int limit) {
        Pagination p = Pagination.of(page, limit);
        return repository.getAllServices(p.limit(), p.offset());
    }

    public List<Map<String, Object>> searchProviders(String keyword, int page, int limit) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword 'q' must not be empty");
        }
        Pagination p = Pagination.of(page, limit);
        return repository.searchProviders(keyword.trim(), p.limit(), p.offset());
    }

    public List<Map<String, Object>> getTopProviders(int top) {
        int safeTop = Math.min(Math.max(top, 1), 50);
        return repository.getTopProviders(safeTop);
    }

    public List<Map<String, Object>> getAvailableProviders(String day, int page, int limit) {
        if (day == null || day.isBlank()) {
            throw new IllegalArgumentException("Query param 'day' is required");
        }
        Pagination p = Pagination.of(page, limit);
        return repository.getAvailableProviders(day.trim(), p.limit(), p.offset());
    }
}
