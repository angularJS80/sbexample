package com.example.demo.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration
@Lazy
@EnableTransactionManagement
@MapperScan(basePackages = {"com.example.demo"})
class DefaultDatabaseConfig{
 
    @Autowired
    private ApplicationContext applicationContext;
 
    /*jdbc lock up*/
    @Primary
    @Bean 
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
      return DataSourceBuilder.create().build();
    }
    /* jndi luck up
    @Bean
    public DataSource dataSource() {      		
   		    JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
   	        bean.setJndiName("java:comp/env/jdbc/mysql");
   	        bean.setProxyInterface(DataSource.class);
   	        
   	        bean.setLookupOnStartup(false);
   	        try
   	        {
   	            bean.afterPropertiesSet();
   	        }
   	        catch (NamingException e)
   	        {
   	            System.out.println(e);
   	        }
   	        DataSource ds = (DataSource) bean.getObject();
   	        
   	        return ds; 
   	       
   	       
       }
       */
 
    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
 
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setTypeAliasesPackage("com.stunstun.spring.repository.entity");
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*.xml"));
        return sessionFactoryBean.getObject();
    }
}