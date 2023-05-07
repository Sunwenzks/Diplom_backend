package ru.skydiver.backend.skydiver.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.skydiver.backend.skydiver.model.CartProductEntity;

@Component
public class CartRepository {
    private static final String CART_TABLE_NAME = "cart";
    private static final String PRODUCT_TABLE_NAME = "product";

    private static final CartRowMapper ROW_MAPPER = new CartRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CartRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartProductEntity> getCartByUser(String userId) {
        var sql = "select * from " + CART_TABLE_NAME + " c " +
                "join " + PRODUCT_TABLE_NAME + " p on c.product_id = p.id" +
                " where c.user_id = :userId";
        var params = Map.of("userId", userId);
        return jdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public void addToCart(String userId, int productId, int amount) {
        var sql = "insert into " + CART_TABLE_NAME + "(user_id, product_id, amount)" +
                " VALUES (:userId, :productId, :amount)";
        var params = Map.of(
                "userId", userId,
                "productId", productId,
                "amount", amount
        );
        jdbcTemplate.update(sql, params);
    }

    private static class CartRowMapper implements RowMapper<CartProductEntity> {
        @Override
        public CartProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            var userId = rs.getString("user_id");
            var productId = rs.getInt("product_id");
            var amount = rs.getInt("amount");
            var productName = rs.getString("name");
            var imageUrl = rs.getString("image_url");
            var price = rs.getInt("price");
            return new CartProductEntity(userId, productId, amount, productName, imageUrl, price);
        }
    }
}
