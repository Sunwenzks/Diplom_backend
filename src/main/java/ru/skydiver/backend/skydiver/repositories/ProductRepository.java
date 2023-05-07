package ru.skydiver.backend.skydiver.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.skydiver.backend.skydiver.model.ProductEntity;

@Component
public class ProductRepository {
    private static final String TABLE_NAME = "product";
    private final ProductRowMapper ROW_MAPPER = new ProductRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public ProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<ProductEntity> searchProduct(String searchString) {
        var sql = "select * from " + TABLE_NAME + " where name ilike :searchString";
        var params = Map.of("searchString", "%" + searchString + "%");
        return jdbcTemplate.query(sql, params, ROW_MAPPER);
    }
    public Optional<ProductEntity> getSearchProduct(Integer productId) {
        var sql = "select * from " + TABLE_NAME + " where id = :productId";
        var params = Map.of("productId" , productId);
        return jdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }
    private static class ProductRowMapper implements RowMapper<ProductEntity> {
        @Override
        public ProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id_product = rs.getInt("id");
            var name = rs.getString("name");
            var id_category = rs.getInt("category_id");
            var price = rs.getInt("price");
            var productURL = rs.getString("image_url");
            var product_description = rs.getString("description");
            return new ProductEntity(id_product, name, id_category, price, productURL, product_description);
        }
    }
}
