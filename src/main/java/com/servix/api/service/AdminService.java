package com.servix.api.service;

import com.servix.api.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final AdminRepository repository;

    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    public List<Map<String, Object>> getRevenueByCategory() {
        return repository.getRevenueByCat();
    }

    public List<Map<String, Object>> getLeaderboard(boolean fast) {
        return fast ? repository.getLeaderboardFast() : repository.getLeaderboard();
    }

    public List<Map<String, Object>> getComplaintDashboard() {
        return repository.getComplaintDashboard();
    }

    public List<Map<String, Object>> getCityRevenue() {
        return repository.getCityRevenue();
    }

    public Map<String, Object> refreshViews() {
        return repository.refreshViews();
    }
}
