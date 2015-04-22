package com.taxholic.configuration.beans;


import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DBConfiguration {
	
	 private @Value("${mysql.jdbc.driverClassName}") String driverClassName;
	 private @Value("${mysql.jdbc.url}") String url;
	 private @Value("${mysql.jdbc.userName}") String userName;
	 private @Value("${mysql.jdbc.password}") String password;
	 private @Value("${mysql.jdbc.initialSize}") int initialSize;
	 private @Value("${mysql.jdbc.maxActive}") int maxActive;
	 private @Value("${mysql.jdbc.maxIdle}") int maxIdle;
	 private @Value("${mysql.jdbc.maxWait}") int maxWait;
	 
	 
	 private @Value("${sqllite.jdbc.driverClassName}") String sqlliteDriverClassName;
	 private @Value("${sqllite.jdbc.url}") String sqlliteUrl;

	 
	  //------------------------------------------------------------------------ MySql
    @Bean(destroyMethod = "close")
    public BasicDataSource  dataSource() {
    	
    	BasicDataSource dataSource = new BasicDataSource();
    	
    	dataSource.setDriverClassName(driverClassName);
    	dataSource.setUrl(url);
    	dataSource.setUsername(userName);
    	dataSource.setPassword(password);
    	dataSource.setInitialSize(initialSize);
    	dataSource.setMaxActive(maxActive);
    	dataSource.setMaxIdle(maxIdle);
    	dataSource.setMaxWait(maxWait);

        return dataSource;
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
    	
    	SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
		sqlSessionFactory.setConfigLocation(defaultResourceLoader.getResource("classpath:config/mybatis-config.xml"));
		sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/**/*.xml"));

		return sqlSessionFactory.getObject();
    }
    
    @Bean(destroyMethod = "clearCache")
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
		SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory);
		return sqlSession;
	}
    
    @Bean
	public DataSourceTransactionManager txManager(BasicDataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
    
    
    
    //------------------------------------------------------------------------ Sqlite
    
    @Bean(destroyMethod = "close")
    public BasicDataSource  sqliteSource() {
    	
    	BasicDataSource sqliteSource = new BasicDataSource();
    	
    	sqliteSource.setDriverClassName(sqlliteDriverClassName);
    	sqliteSource.setUrl(sqlliteUrl);
    	
    	return sqliteSource;
    }

    @Bean
    public SqlSessionFactory sqlliteSessionFactory() throws Exception {
    	
    	SqlSessionFactoryBean sqlliteSessionFactory = new SqlSessionFactoryBean();
    	sqlliteSessionFactory.setDataSource(sqliteSource());
		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
		sqlliteSessionFactory.setConfigLocation(defaultResourceLoader.getResource("classpath:config/mybatis-config.xml"));
		sqlliteSessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/**/*.xml"));

		return sqlliteSessionFactory.getObject();
    }
    
    @Bean(destroyMethod = "clearCache")
	public SqlSessionTemplate sqliteSession(SqlSessionFactory sqlliteSessionFactory) {
		SqlSessionTemplate sqliteSession = new SqlSessionTemplate(sqlliteSessionFactory);
		return sqliteSession;
	}
    
//    @Bean
//	public DataSourceTransactionManager sqlitetxManager(@Qualifier("sqliteSource") BasicDataSource sqliteSource) {
//		return new DataSourceTransactionManager(sqliteSource);
//	}
    
}