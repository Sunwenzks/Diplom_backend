package ru.skydiver.backend.skydiver.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.skydiver.backend.skydiver.model.ContactEntity;
import ru.skydiver.backend.skydiver.model.ContactTypes;

@Component
public class ContactRepository {

    private static final String CONTACT_TABLE = "contacts";
    private final ContactRowMapper ROW_MAPPER = new ContactRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ContactRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<ContactEntity> findByContact(String contact) {
        var sql = "select * from " + CONTACT_TABLE + " where contact = :param";
        var params = Map.of("param", "%" + contact + "%");
        return jdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }

    public void addContactToUser(ContactTypes type, String contact, String userName) {
        var sql = "insert into " + CONTACT_TABLE + " (user_name, contact, contact_type) VALUES " +
                "(:uId, :contact, :contactType)";
        var params = Map.of("uId", userName, "contact", contact, "contactType", type.name());
        jdbcTemplate.update(sql, params);
    }

    private static class ContactRowMapper implements RowMapper<ContactEntity> {
        @Override
        public ContactEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getInt("id");
            var userName = rs.getString("user_name");
            var contact = rs.getString("contact");
            var contactType = ContactTypes.valueOf(rs.getString("contact_type"));
            return new ContactEntity(id, userName, contact, contactType);
        }
    }
}
