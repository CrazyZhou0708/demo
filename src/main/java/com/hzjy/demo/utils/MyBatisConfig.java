package com.hzjy.demo.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by wxp on 2016/11/16.
 */
@Configuration
@Component
@EnableTransactionManagement(order = 2)
public class MyBatisConfig implements EnvironmentAware {

    private Environment environment;

    @Bean
    @Primary
    public DataSource druidDataSource() throws Exception{
        Properties props = new Properties();
        props.put("maxWait",environment.getProperty("spring.datasource.maxWait"));
        props.put("name",environment.getProperty("spring.datasource.name"));
        props.put("driverClassName", environment.getProperty("spring.datasource.driver-class-name"));
        props.put("url", environment.getProperty("spring.datasource.url"));
        props.put("username", environment.getProperty("spring.datasource.username"));
        props.put("password", environment.getProperty("spring.datasource.password"));
        props.put("keepAlive", environment.getProperty("spring.datasource.keepAlive"));
        props.put("maxEvictableIdleTimeMillis", environment.getProperty("spring.datasource.maxEvictableIdleTimeMillis"));
        props.put("initialSize", environment.getProperty("spring.datasource.initialSize"));
        props.put("minIdle", environment.getProperty("spring.datasource.minIdle"));
        props.put("maxActive", environment.getProperty("spring.datasource.maxActive"));
        props.put("timeBetweenEvictionRunsMillis", environment.getProperty("spring.datasource.timeBetweenEvictionRunsMillis"));
        props.put("minEvictableIdleTimeMillis", environment.getProperty("spring.datasource.minEvictableIdleTimeMillis"));
        props.put("validationQuery", environment.getProperty("spring.datasource.validationQuery"));
        props.put("testWhileIdle", environment.getProperty("spring.datasource.testWhileIdle"));
        props.put("testOnBorrow", environment.getProperty("spring.datasource.testOnBorrow"));
        props.put("testOnReturn", environment.getProperty("spring.datasource.testOnReturn"));
        props.put("poolPreparedStatements", environment.getProperty("spring.datasource.poolPreparedStatements"));
        props.put("maxPoolPreparedStatementPerConnectionSize", environment.getProperty("spring.datasource.maxPoolPreparedStatementPerConnectionSize"));
        props.put("removeAbandoned", environment.getProperty("spring.datasource.removeAbandoned"));
        props.put("removeAbandonedTimeout", environment.getProperty("spring.datasource.removeAbandonedTimeout"));
        props.put("logAbandoned", environment.getProperty("spring.datasource.logAbandoned"));
        props.put("connectionProperties", environment.getProperty("spring.datasource.connectionProperties"));
        props.put("filters", environment.getProperty("spring.datasource.filters"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource ds) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ds);
        bean.setTypeAliasesPackage("com.hzjy.demo.pojo");
/*        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});*/
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:com/hzjy/demo/mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

/*    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }*/

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() throws Exception {
        final MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.hzjy.demo.mapper");
        msc.afterPropertiesSet();
        return msc;
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /*@Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }*/
}












