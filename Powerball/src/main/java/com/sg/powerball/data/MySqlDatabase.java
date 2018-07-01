/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.powerball.data;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

// Once you're full-blown JPA, you don't really need this class.
// It remains for reference.
// Without JPA, uncomment @Component below.
@Component
public class MySqlDatabase {

    // This is needed for @Transactional support along 
    // with @EnableTransactionManagement in App.java.
    @Bean
    public DataSourceTransactionManager getTransactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }


    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource getDataSource() throws SQLException {

        Properties dbProperties = new Properties();
        try (InputStream stream = MySqlDatabase.class.getClassLoader().getResourceAsStream("db.properties")) {
            dbProperties.load(stream);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(100);
        }

        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName(dbProperties.getProperty("serverName"));
        ds.setDatabaseName(dbProperties.getProperty("databaseName"));
        ds.setUser(dbProperties.getProperty("userName"));
        ds.setPassword(dbProperties.getProperty("password"));
        ds.setServerTimezone("UTC");
        ds.setUseSSL(false);

        return ds;
    }
}
