package com.servix.api.service;

import com.servix.api.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final AdminRepository dao;

    public AdminService(AdminRepository dao) {
        this.dao = dao;
    }

    public List<Map<String, Object>> getRevenueByCategory() {
        return dao.getRevenueByCat();
    }

    public List<Map<String, Object>> getLeaderboard(boolean useFastRead) {
        return useFastRead ? dao.getLeaderboardFast() : dao.getLeaderboard();
    }

    public List<Map<String, Object>> getComplaintDashboard() {
        return dao.getComplaintDashboard();
    }

    public Map<String, Object> refreshViews() {
        return dao.refreshViews();
    }
}
