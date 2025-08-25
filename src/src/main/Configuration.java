package main;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

// почему-то IDE подставляет полный путь
@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:postgresql://localhost:5435/cmygehm_db?currentSchema=homework_4");
        config.setUsername("postgres");
        config.setPassword("postgres");

        config.setPoolName("SpringHikariPool");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        config.setAutoCommit(true);

        return new HikariDataSource(config);
    }
}
