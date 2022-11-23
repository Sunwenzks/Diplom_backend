package ru.skydiver.backend.skydiver.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.skydiver.backend.skydiver.model.CategoryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CategoryRepository {

    private static final String TABLE_NAME = "public.\"Category\"";
    private final CategoryRowMapper ROW_MAPPER = new CategoryRowMapper();

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; //для работы с бд(извлекать и представялять инфу)
    }

    public List<CategoryDto> getAllCategories() {
        var sql = "select * from " + TABLE_NAME;
       return jdbcTemplate.query(sql, ROW_MAPPER);
    }
    public List<CategoryDto> getMainCategory(){
        var sql = "select * from " + TABLE_NAME + "where main_page = true";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public String getCategory(String categoryName) {
        return "";
    }

    public void addCategory(String categoryName) {

    }

    private class CategoryRowMapper implements RowMapper<CategoryDto> {
        @Override
        public CategoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getInt("id");
            var name = rs.getString("name");
            var isMain = rs.getBoolean("main_page");
            return new CategoryDto(id, name, isMain);
        }
    }
}
