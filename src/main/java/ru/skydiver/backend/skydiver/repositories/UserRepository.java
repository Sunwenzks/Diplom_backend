package ru.skydiver.backend.skydiver.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.skydiver.backend.skydiver.model.UserEntity;

@Component
public class UserRepository {
    private static final String TABLE_NAME = "users";
    private final UserRowMapper ROW_MAPPER = new UserRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserEntity> getUserList() {
        var sql = "select * from " + TABLE_NAME;
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public void banUser(String userName, boolean status) {
        var sql = "update " + TABLE_NAME +
                " set enabled = :status " +
                " where username = :userName";
        jdbcTemplate.update(sql, Map.of(
                "status", status,
                "userName", userName));
    }

    public Optional<UserEntity> loadByUserName(String userName) {
        var sql = "select * from " + TABLE_NAME +
                " where username = :userName";
        return jdbcTemplate.query(sql, Map.of("userName", userName), ROW_MAPPER)
                .stream().findAny();
    }



    private static class UserRowMapper implements RowMapper<UserEntity> {
        @Override
        public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getInt("id");
            var name = rs.getString("username");
            var enabled = rs.getBoolean("enabled");
            return new UserEntity(id, name, enabled);
        }
    }
}
