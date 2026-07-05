package com.servix.api.service;

import com.servix.api.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProviderService {

    private final ProviderRepository repository;

    public ProviderService(ProviderRepository repository) {
        this.repository = repository;
    }

    public List<Map<String, Object>> getPendingJobs(int providerId, int page, int limit) {
        Pagination p = Pagination.of(page, limit);
        return repository.getPendingJobs(providerId, p.limit(), p.offset());
    }

    public List<Map<String, Object>> getCompletedJobs(int providerId, int page, int limit) {
        Pagination p = Pagination.of(page, limit);
        return repository.getCompletedJobs(providerId, p.limit(), p.offset());
    }

    public List<Map<String, Object>> getEarnings(int providerId) {
        return repository.getEarnings(providerId);
    }

    public List<Map<String, Object>> getReviews(int providerId, int page, int limit) {
        Pagination p = Pagination.of(page, limit);
        return repository.getReviews(providerId, p.limit(), p.offset());
    }

    public List<Map<String, Object>> getRatingComparison(int providerId) {
        return repository.getRatingComparison(providerId);
    }
}
