package ru.skydiver.backend.skydiver.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getCategory(String categoryName) {
        return "";
    }

    public void addCategory(String categoryName) {

    }
}