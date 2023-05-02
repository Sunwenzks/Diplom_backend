package ru.skydiver.backend.skydiver.repositories;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.skydiver.backend.skydiver.model.CategoryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CategoryRepository {
    private static final String TABLE_NAME = "category";
    private final CategoryRowMapper ROW_MAPPER = new CategoryRowMapper();
    private final JdbcTemplate jdbcTemplate;
    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<CategoryDto> getAllCategories() {
        var sql = "select * from " + TABLE_NAME;
       return jdbcTemplate.query(sql, ROW_MAPPER);
    }
    public List<CategoryDto> getMainCategory(){
        var sql = "select * from " + TABLE_NAME + " where main_page = true";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }
    public String getCategory(String categoryName) {
        throw new NotImplementedException();
    }
    public void addCategory(String categoryName) {
        throw new NotImplementedException();
    }
    private static class CategoryRowMapper implements RowMapper<CategoryDto> {
        @Override
        public CategoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getInt("id");
            var name = rs.getString("name");
            var isMain = rs.getBoolean("main_page");
            var imageURL = rs.getString("pictures");
            return new CategoryDto(id, name, isMain, imageURL);
        }
    }
}
