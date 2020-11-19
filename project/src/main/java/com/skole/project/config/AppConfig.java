package com.skole.project.config;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.skole.project")
@EnableTransactionManagement
@PropertySource(value="classpath:application.properties")
public class AppConfig {

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DatabaseProperties skolaDatabaseProperties() {
		return new DatabaseProperties();
	}
	
	@Bean(name="skolaJdbcTemplate")
	public JdbcTemplate skolaJdbcTemplate() {
		return new JdbcTemplate(skolaDataSource());
	}
	
	@Bean
	@Primary
	public DataSource skolaDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		
		dataSource.setDriverClassName(skolaDatabaseProperties().getDriverClassName());
		dataSource.setJdbcUrl(skolaDatabaseProperties().getUrl());
		dataSource.setUsername(skolaDatabaseProperties().getUsername());
		dataSource.setPassword(skolaDatabaseProperties().getPassword());
		
		dataSource.addDataSourceProperty("cachePrepStmts", true);
		dataSource.addDataSourceProperty("perpStmtCacheSize", 25000);
		dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 20048);
		dataSource.addDataSourceProperty("useServerPrepStmts", true);
		dataSource.addDataSourceProperty("initializationFailFast", true);
		
		dataSource.setPoolName("DS1_HIKARICP_CONNECTION_POOL");
		
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager skolaPTM() {
		return new DataSourceTransactionManager(skolaDataSource());
	}
	
}
