package ru.skydiver.backend.skydiver.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.skydiver.backend.skydiver.model.CategoryEntity;

@Component
public class CategoryRepository {
    private static final String TABLE_NAME = "category";
    private final CategoryRowMapper ROW_MAPPER = new CategoryRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CategoryEntity> getAllCategories() {
        var sql = "select * from " + TABLE_NAME;
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public List<CategoryEntity> getMainCategory() {
        var sql = "select * from " + TABLE_NAME + " where main_page = true";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Optional<CategoryEntity> getCategory(String categoryName) {
        var sql = "select * from " + TABLE_NAME +
                " where name = :n";
        var params =  Map.of("n", categoryName);
        return jdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }

    public void addCategory(CategoryEntity category) {
        var sql = "insert into " + TABLE_NAME +
                "(name, main_page, image_url) VALUES (:name, :page, :image)";
        jdbcTemplate.update(sql,
                Map.of("name", category.getName(),
                        "page", category.isMainPage(),
                        "image", category.getImageURL()));
    }

    public void removeCategory(int categoryId) {
        var sql = "delete from " + TABLE_NAME +
                " where id = :id";
        jdbcTemplate.update(sql, Map.of("id", categoryId));
    }

    public void updateCategory(Integer categoryId, String categoryName, Boolean mainCategory, String url) {
        var sql = "update " + TABLE_NAME +
                " set " +
                " name = :name, " +
                " main_page = :main, " +
                " image_url = :url " +
                " where id = :id";
        jdbcTemplate.update(sql,
                Map.of(
                        "name", categoryName,
                        "main", mainCategory,
                        "url", url,
                        "id", categoryId
                ));
    }

    private static class CategoryRowMapper implements RowMapper<CategoryEntity> {
        @Override
        public CategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getInt("id");
            var name = rs.getString("name");
            var isMain = rs.getBoolean("main_page");
            var imageURL = rs.getString("image_url");
            return new CategoryEntity(id, name, isMain, imageURL);
        }
    }
}
