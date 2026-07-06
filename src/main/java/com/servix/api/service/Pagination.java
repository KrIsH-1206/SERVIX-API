package com.servix.api.service;

/**
 * Normalizes raw pageNumber/pageSize request params into a safe
 * pageSize + rowOffset pair. Centralizing this here keeps every
 * service consistent and keeps the "(pageNumber - 1) * pageSize"
 * arithmetic out of the repository/SQL layer.
 */
record Pagination(int pageSize, int rowOffset) {

    private static final int MAX_PAGE_SIZE = 100;

    static Pagination of(int pageNumber, int requestedPageSize) {
        if (pageNumber < 1) {
            throw new IllegalArgumentException("Query param 'page' must be >= 1");
        }
        if (requestedPageSize < 1 || requestedPageSize > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Query param 'limit' must be between 1 and " + MAX_PAGE_SIZE);
        }
        int rowOffset = (pageNumber - 1) * requestedPageSize;
        return new Pagination(requestedPageSize, rowOffset);
    }
}
