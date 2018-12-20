package com.qingyang.test;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingsphere.api.algorithm.masterslave.RoundRobinMasterSlaveLoadBalanceAlgorithm;
import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Created by qinggyang on 2017/5/10.
 */
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
    @Bean(name = "masterDataSource")
    DataSource masterDataSource() throws IOException {
        DruidDataSource ds = new DruidDataSource();
        Properties p = PropertiesLoaderUtils.loadAllProperties("a.properties");
        ds.configFromPropety(p);
        return ds;
    }

    @Bean(name = "salve1DataSource")
    DataSource slave1DataSource() throws IOException {
        DruidDataSource ds = new DruidDataSource();
        Properties p = PropertiesLoaderUtils.loadAllProperties("b.properties");
        ds.configFromPropety(p);
        return ds;
    }

//    @Bean
//    HintManager hintManager(){
//
//        return HintManager.getInstance();
//    }

    @Bean(name = "sharingDataSource")
    DataSource shardingDataSource(@Qualifier(value = "masterDataSource") DataSource masterDataSource
            , @Qualifier("salve1DataSource") DataSource salve1DataSource) throws SQLException {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig =
                new MasterSlaveRuleConfiguration(
                        "sharingDataSource",
                        "ds_master",
                        Arrays.asList("ds_slave0"),
                        new RoundRobinMasterSlaveLoadBalanceAlgorithm());
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds_master", masterDataSource);
        dataSourceMap.put("ds_slave0", salve1DataSource);
        return MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfig, new HashMap(), new Properties());
    }


    @Bean
    SqlSessionFactory sqlSessionFactoryBean(@Qualifier(value="sharingDataSource") DataSource sharingDataSource) throws Exception {
        SqlSessionFactoryBean ssf = new SqlSessionFactoryBean();
        ssf.setDataSource(sharingDataSource);
        ssf.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
        return ssf.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    DataSourceTransactionManager dataSourceTransactionManager(@Qualifier(value="sharingDataSource") DataSource sharingDataSource) {
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(sharingDataSource);
        return dstm;
    }
}
