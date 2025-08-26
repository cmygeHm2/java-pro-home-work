package hw.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    @Value("${db.url}")
    private String jdbcUrl;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.pool.maximumPoolSize:10}")
    private int maximumPoolSize;

    @Value("${db.pool.minimumIdle:2}")
    private int minimumIdle;

    @Value("${db.pool.poolName:SpringHikariPool}")
    private String poolName;

    @Value("${db.pool.autoCommit:true}")
    private boolean autoCommit;

    @Value("${db.schema}")
    private String schema;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);

        config.setMaximumPoolSize(maximumPoolSize);
        config.setMinimumIdle(minimumIdle);
        config.setPoolName(poolName);
        config.setAutoCommit(autoCommit);

        Properties dataSourceProperties = new Properties();
        dataSourceProperties.setProperty("currentSchema", schema);
        dataSourceProperties.setProperty("search_path", schema);
        config.setDataSourceProperties(dataSourceProperties);

        return new HikariDataSource(config);
    }
}
