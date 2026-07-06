package com.servix.api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {

    private final JdbcTemplate db;

    public CustomerRepository(JdbcTemplate db) {
        this.db = db;
    }

    // ── My Bookings ──────────────────────────────────────────
    public List<Map<String, Object>> getBookings(int customerId, int pageSize, int rowOffset) {
        return db.queryForList("""
                SELECT b.booking_id, u.email AS provider_email, b.scheduled_date,
                       b.scheduled_time, b.status, b.total_amount
                FROM bookings b
                JOIN service_providers sp ON b.provider_id = sp.provider_id
                JOIN users u ON sp.user_id = u.user_id
                WHERE b.customer_id = ?
                ORDER BY b.scheduled_date DESC
                LIMIT ? OFFSET ?
                """, customerId, pageSize, rowOffset);
    }

    // ── Booking Items (Itemized) ─────────────────────────────
    public List<Map<String, Object>> getBookingItems(int bookingId) {
        return db.queryForList("""
                SELECT bi.item_no, s.service_name, sv.variant_name,
                       bi.quantity, bi.unit_price,
                       (bi.quantity * bi.unit_price) AS line_total
                FROM booking_items bi
                JOIN services s ON bi.service_id = s.service_id
                LEFT JOIN service_variants sv ON bi.variant_id = sv.variant_id
                WHERE bi.booking_id = ?
                ORDER BY bi.item_no
                """, bookingId);
    }

    // ── Spending Summary (CASE, SUM, COUNT, GROUP BY) ────────
    public List<Map<String, Object>> getSpendingSummary(int customerId) {
        return db.queryForList("""
                SELECT
                    COUNT(*)                                           AS total_bookings,
                    SUM(CASE WHEN status = 'completed' THEN 1 ELSE 0 END)  AS completed,
                    SUM(CASE WHEN status = 'cancelled' THEN 1 ELSE 0 END)  AS cancelled,
                    SUM(CASE WHEN status = 'pending'   THEN 1 ELSE 0 END)  AS pending,
                    SUM(total_amount)                                  AS total_spent,
                    ROUND(AVG(total_amount), 2)                        AS avg_per_booking
                FROM bookings
                WHERE customer_id = ?
                """, customerId);
    }

    // ── Saved Addresses (4-table JOIN via locations) ─────────
    public List<Map<String, Object>> getAddresses(int customerId) {
        return db.queryForList("""
                SELECT loc.location_id, ca.label, loc.street, loc.landmark,
                       ar.area_name, ar.pincode, ci.city_name
                FROM customer_addresses ca
                JOIN locations loc ON ca.location_id = loc.location_id
                JOIN areas ar ON loc.area_id = ar.area_id
                JOIN cities ci ON ar.city_id = ci.city_id
                WHERE ca.customer_id = ?
                ORDER BY ca.label
                """, customerId);
    }
}
