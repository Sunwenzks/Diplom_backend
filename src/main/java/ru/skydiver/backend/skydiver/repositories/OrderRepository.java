package ru.skydiver.backend.skydiver.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.skydiver.backend.skydiver.model.OrderEntity;
import ru.skydiver.backend.skydiver.model.OrderStatuses;

@Component
public class OrderRepository {

    private static final String ORDER_TABLE_NAME = "orders";

    private static final String ORDER_ITEMS_TABLE_NAME = "orders_items";

    private static final OrderRowMapper ORDER_ROW_MAPPER = new OrderRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void changeStatus(int orderId, OrderStatuses status) {
        var sql = "update " + ORDER_TABLE_NAME +
                "set status = :status" +
                "where id = :orderId";
        jdbcTemplate.update(sql, Map.of("status", status.name(), "orderId", orderId));
    }

    public List<OrderEntity> getOrders() {
        var sql = "select * from " + ORDER_TABLE_NAME;
        return jdbcTemplate.query(sql, ORDER_ROW_MAPPER);
    }

    public int createOrder(String userId) {
        var keyHolder = new GeneratedKeyHolder();
        var sql = "insert into " + ORDER_TABLE_NAME +
                " (user_id, status) VALUES (?,'"+ OrderStatuses.NEW.name() +"')";
        jdbcTemplate.getJdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userId);
            return ps;
        }, keyHolder);
        return (int) keyHolder.getKeys().get("id");
    }

    public void addItemsToOrder(int orderId,  Map<Integer, Integer> productAmount) {
        var sql = "insert into " + ORDER_ITEMS_TABLE_NAME +
                "(order_id, product_id, amount) VALUES (:orderId, :productId, :amount)";
        var params = SqlParameterSourceUtils.createBatch(
            productAmount.entrySet().stream().map(pair -> createParamMap(orderId, pair)).toArray());
        jdbcTemplate.batchUpdate(sql, params);
    }

    private Map<String,Object> createParamMap(int orderId, Map.Entry<Integer, Integer> map) {
        return Map.of(
                "orderId", orderId,
                "productId", map.getKey(),
                "amount", map.getValue()
        );
    }

    private static class OrderRowMapper implements RowMapper<OrderEntity> {
        @Override
        public OrderEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getInt("id");
            var name = rs.getString("user_id");
            var status = OrderStatuses.valueOf(rs.getString("status"));
            return new OrderEntity(id, name, status);
        }
    }
}
