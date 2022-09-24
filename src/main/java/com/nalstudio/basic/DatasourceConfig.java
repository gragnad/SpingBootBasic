package com.nalstudio.basic;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Slf4j
@Configuration
@MapperScan(basePackages= "mapper", sqlSessionFactoryRef="sqlSessionFactory")
public class DatasourceConfig {

    @Bean(name="dataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource getDataSource() {
        return new HikariDataSource();
    }

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("dataSource") DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/*.xml"));

        return sqlSessionFactory.getObject();
    }

    @Bean(name="sqlSession")
    public SqlSessionFactory sqlSession(SqlSessionFactory sqlSessionFactory) throws Exception {
        return sqlSessionFactory;
    }

}
