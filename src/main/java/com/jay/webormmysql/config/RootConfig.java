package com.jay.webormmysql.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"com.jay.webormmysql.repositories"})
@EnableTransactionManagement
@PropertySources({@PropertySource(name = "db", value = "classpath:db.properties")})
public class RootConfig {
	
	@Autowired
	private Environment env;
	
	 @Bean
	   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	      em.setDataSource(dataSource());
	      em.setPackagesToScan(new String[] { "com.jay.webormmysql.model" });
	 
	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	      em.setJpaVendorAdapter(vendorAdapter);
	      em.setJpaProperties(additionalProperties());
	 
	      return em;
	   }
	
	  @Bean
	   public DataSource dataSource(){
		  HikariConfig hikariConfig = new HikariConfig();
	      //DriverManagerDataSource dataSource = new DriverManagerDataSource();
		  hikariConfig.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		  hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
	      Properties dataSourceProperties = new Properties();
	      dataSourceProperties.put("user", env.getProperty("jdbc.username"));
	      dataSourceProperties.put("password", env.getProperty("jdbc.password"));
	      hikariConfig.setDataSourceProperties(dataSourceProperties);
	      HikariDataSource dataSource = new HikariDataSource(hikariConfig);
	      return dataSource;
	   }
	  
	   @Bean
	   public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
	      JpaTransactionManager transactionManager = new JpaTransactionManager();
	      transactionManager.setEntityManagerFactory(emf);
	 
	      return transactionManager;
	   }
	 
	   @Bean
	   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	      return new PersistenceExceptionTranslationPostProcessor();
	   }
	 
	   Properties additionalProperties() {
	      Properties properties = new Properties();
	      properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	      properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
	      return properties;
	   }

}
