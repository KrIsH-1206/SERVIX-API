package com.servix.api.service;

import com.servix.api.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProviderService {

    private final ProviderRepository dao;

    public ProviderService(ProviderRepository dao) {
        this.dao = dao;
    }

    public List<Map<String, Object>> getPendingJobs(int providerId, int pageNumber, int pageSize) {
        Pagination pageInfo = Pagination.of(pageNumber, pageSize);
        return dao.getPendingJobs(providerId, pageInfo.pageSize(), pageInfo.rowOffset());
    }

    public List<Map<String, Object>> getCompletedJobs(int providerId, int pageNumber, int pageSize) {
        Pagination pageInfo = Pagination.of(pageNumber, pageSize);
        return dao.getCompletedJobs(providerId, pageInfo.pageSize(), pageInfo.rowOffset());
    }

    public List<Map<String, Object>> getEarnings(int providerId) {
        return dao.getEarnings(providerId);
    }

    public List<Map<String, Object>> getRatingComparison(int providerId) {
        return dao.getRatingComparison(providerId);
    }
}
