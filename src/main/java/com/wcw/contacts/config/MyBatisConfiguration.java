package com.wcw.contacts.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 王成伍
 */
@Configuration
/**开启事务管理的注解*/
@EnableTransactionManagement
@MapperScan(basePackages = "com.dataService.mappers.*" ,
        sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MyBatisConfiguration {
    private static Logger logger = LoggerFactory.getLogger(MyBatisConfiguration.class);
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.maxWait}")
    private int maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    /**
     * 配置druid进行sql性能监控
     */
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("{spring.datasource.connectionProperties}")
    private Properties connectionProperties;
    @Value("${spring.datasource.useGlobalDataSourceStat}")
    private boolean useGlobalDataSourceStat;
    @Value("${spring.datasource.druidLoginName}")
    private String druidLoginName;
    @Value("${spring.datasource.druidPassword}")
    private String druidPassword;
    /**
     * 配置mybaitis
     */
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    public MyBatisConfiguration() {
    }

    /**
     * @Author:
     * @Description: 数据源, 也可以使用这个注解 @ConfigurationProperties(prefix = "")
     * @Date: 2018/4/15 17:15
     * @Modified by:
     * @params: * @Primary多数据源时必须加上，表示哪个为主
     * @return:
     */
    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(this.url);
        druidDataSource.setUsername(this.username);
        druidDataSource.setPassword(this.password);
        druidDataSource.setDriverClassName(this.driverClass);
        druidDataSource.setInitialSize(this.initialSize);
        druidDataSource.setMaxActive(this.maxActive);
        druidDataSource.setMinIdle(this.minIdle);
        druidDataSource.setMaxWait(this.maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(
                this.timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(
                this.minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(this.validationQuery);
        druidDataSource.setTestOnBorrow(this.testOnBorrow);
        druidDataSource.setTestOnReturn(this.testOnReturn);
        druidDataSource.setTestWhileIdle(this.testWhileIdle);
        druidDataSource.setPoolPreparedStatements(this.poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(
                this.maxPoolPreparedStatementPerConnectionSize);
        druidDataSource.setConnectProperties(this.connectionProperties);
        druidDataSource.setUseGlobalDataSourceStat(this.useGlobalDataSourceStat);
        try {
            druidDataSource.setFilters(this.filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        return druidDataSource;
    }

    /**
     * @Author:
     * @Description: 会话工厂
     * @Date: 2018/4/15 17:15
     * @Modified by:
     * @params: * @param null
     * @return:
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource")
                                                             DataSource masterDataSource) throws Exception {
        logger.info("load SpringBootVFS");
        /**DefaultVFS在获取jar上存在问题，使用springboot只能修改*/
        VFS.addImplClass(SpringBootVFS.class);
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(masterDataSource);
        PathMatchingResourcePatternResolver resolver
                = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(this.mapperLocations);
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setTypeAliasesPackage(this.typeAliasesPackage);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * @Author:
     * @Description: 事务
     * @Date: 2018/4/15 17:16
     * @Modified by:
     * @params: * @param null
     * @return:
     */
    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(
            @Qualifier("masterDataSource") DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }

}