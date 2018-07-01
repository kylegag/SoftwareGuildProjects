/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movie.dao;


import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

@Component
public class MySqlDatabase {
   

    // This is needed for @Transactional support along 
    // with @EnableTransactionManagement in App.java.
    @Bean
    public DataSourceTransactionManager getTransactionManager() throws SQLException {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() throws SQLException {
        return new JdbcTemplate(getDataSource());
    }

    public static DataSource getDataSource() throws SQLException {

        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("MovieDatabase");
        ds.setUser("root");
        ds.setPassword("root");
        ds.setServerTimezone("UTC");
        ds.setUseSSL(false);

        return ds;
    }

}
