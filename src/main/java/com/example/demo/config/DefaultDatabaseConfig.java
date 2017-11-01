package com.example.demo.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
 
    @Bean
 public DataSource dataSource() {
    		
    		// 가장 기본적인 방식이나 소스를 깃에올리는 순간 디비의 비번이 공개 되버린다. 
    	 	/*
    		DataSource ds = null;
			DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
			String dsUrl = "jdbc:postgresql://127.0.0.1:5432/demo";
			String dsUserName = "postgres";
			String dsPassWord = "aipmocj8+";
			String dsdriverClassName = "org.postgresql.Driver";
			dataSourceBuilder.url(dsUrl);
		    dataSourceBuilder.username(dsUserName);
		    dataSourceBuilder.password(dsPassWord);
		    dataSourceBuilder.driverClassName(dsdriverClassName);
		    ds =  dataSourceBuilder.build();	
		    return ds;
		   */
    	
    		// jndi 방식으로 db접근풀에 관한 룰을 와스가 통재 한다. 
    	    //웹스피어에서 사용했던 방식으로 잘되었었으나 로칼톰켓에서 오류발생.
		    /*
		     * 
		    DataSource ds = null;
		    String jndiName = "java:comp/env/jdbc/postgres";    		
    		JndiDataSourceLookup lookup = new JndiDataSourceLookup();
    		ds = lookup.getDataSource(jndiName);	
    		return ds;
    		*/
    		
    		// jndi 방식으로 db접근풀에 관한 룰을 와스가 통재 한다. 
    		// 로칼톰캣에서 정상 작동.. 와스마다 사용해야 되는 구현채가 다른가 보다. 
    		
		    JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
	        bean.setJndiName("java:comp/env/jdbc/postgres");
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