package config;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;


@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class EmbeddedPostgresConfiguration {
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource(EmbeddedPostgres postgres) throws SQLException {
        DataSource dataSource = postgres.getDatabase("postgres", "postgres");
        try(Statement statement = dataSource.getConnection().createStatement()) {
            statement.execute("CREATE SCHEMA IF NOT EXISTS public");
        }
        return dataSource;
    }

    @Bean
    public EmbeddedPostgres embeddedPostgres() throws IOException {
        EmbeddedPostgres.Builder builder = EmbeddedPostgres.builder();
        return builder.start();
    }

    @Bean
    public TransactionTemplate transactionTemplate(final PlatformTransactionManager txManager) {
        return new TransactionTemplate(txManager);
    }

    @Bean
    public PlatformTransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
