package com.servix.api.service;

/**
 * Normalizes raw page/limit request params into a safe limit + offset pair.
 * Centralizing this here keeps every service consistent and keeps the
 * "page - 1) * limit" arithmetic out of the repository/SQL layer.
 */
record Pagination(int limit, int offset) {

    private static final int MAX_LIMIT = 100;

    static Pagination of(int page, int limit) {
        if (page < 1) {
            throw new IllegalArgumentException("Query param 'page' must be >= 1");
        }
        if (limit < 1 || limit > MAX_LIMIT) {
            throw new IllegalArgumentException("Query param 'limit' must be between 1 and " + MAX_LIMIT);
        }
        int offset = (page - 1) * limit;
        return new Pagination(limit, offset);
    }
}
