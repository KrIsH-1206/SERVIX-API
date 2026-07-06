package com.servix.api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GeneralRepository {

    private final JdbcTemplate db;

    public GeneralRepository(JdbcTemplate db) {
        this.db = db;
    }

    // ── Browse All Services by Category ──────────────────────
    public List<Map<String, Object>> getAllServices(int pageSize, int rowOffset) {
        return db.queryForList("""
                SELECT cat.category_name, s.service_id, s.service_name,
                       s.base_price, s.description
                FROM services s
                JOIN categories cat ON s.category_id = cat.category_id
                ORDER BY cat.category_name, s.base_price
                LIMIT ? OFFSET ?
                """, pageSize, rowOffset);
    }

    // ── Search Providers by Service Keyword ──────────────────
    public List<Map<String, Object>> searchProviders(String query, int pageSize, int rowOffset) {
        return db.queryForList("""
                SELECT sp.provider_id, u.email AS provider_email, ci.city_name,
                       s.service_name, s.base_price, sp.avg_rating, sp.experience_years
                FROM services s
                JOIN provider_services ps ON s.service_id = ps.service_id
                JOIN service_providers sp ON ps.provider_id = sp.provider_id
                JOIN users u ON sp.user_id = u.user_id
                JOIN cities ci ON sp.city_id = ci.city_id
                WHERE s.service_name ILIKE ? AND sp.is_active = TRUE
                ORDER BY sp.avg_rating DESC NULLS LAST
                LIMIT ? OFFSET ?
                """, "%" + query + "%", pageSize, rowOffset);
    }

    // ── Top N Highest-Rated Providers ────────────────────────
    public List<Map<String, Object>> getTopProviders(int topCount) {
        return db.queryForList("""
                SELECT sp.provider_id, u.email, ci.city_name,
                       sp.avg_rating, sp.experience_years
                FROM service_providers sp
                JOIN users u ON sp.user_id = u.user_id
                JOIN cities ci ON sp.city_id = ci.city_id
                WHERE sp.is_active = TRUE AND sp.verification_status = 'verified'
                ORDER BY sp.avg_rating DESC NULLS LAST
                LIMIT ?
                """, topCount);
    }
}
