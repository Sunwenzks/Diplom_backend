package ru.skydiver.backend.skydiver.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.skydiver.backend.skydiver.model.ProductDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ProductRepository {
    private static final String TABLE_NAME = "public.\"Product\"";
    private final ProductRowMapper ROW_MAPPER = new ProductRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public ProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<ProductDto> searchProduct(String searchString) {
        var sql = "select * from " + TABLE_NAME + " where name_product ilike :searchString";
        var params = Map.of("searchString", "%" + searchString + "%");
        return jdbcTemplate.query(sql, params, ROW_MAPPER);
    }
    public Optional<ProductDto> getSearchProduct(Integer productId) {
        var sql = "select * from " + TABLE_NAME + " where id_product = :productId";
        var params = Map.of("productId" , productId);
        return jdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }
    private class ProductRowMapper implements RowMapper<ProductDto> {
        @Override
        public ProductDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id_product = rs.getInt("id_product");
            var name = rs.getString("name_product");
            var id_category = rs.getInt("id_category");
            var price = rs.getInt("price");
            var productURL = rs.getString("pictures_product");
            var product_description = rs.getString("product_description");
            return new ProductDto(id_product, name, id_category, price, productURL, product_description);
        }
    }
}
