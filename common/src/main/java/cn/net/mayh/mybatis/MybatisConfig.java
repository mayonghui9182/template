package cn.net.mayh.mybatis;

import cn.net.mayh.datasource.DataSourceConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * description: mybatis配置类
 * @author : mayonghui
 * @version : 1.0
 * @date : 2020/12/25
 **/
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@DependsOn("appDataSource")
public class MybatisConfig implements TransactionManagementConfigurer {


    @Autowired
    @Qualifier("appDataSource")
    private DataSource appDataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(this.appDataSource);
        //bean.setTypeAliasesPackage("");
        Properties sqlSessionFactoryProperties = new Properties();
        //sqlSessionFactoryProperties.put("plugins", new String[]{"com.github.pagehelper.PageHelper"});
        bean.setConfigurationProperties(sqlSessionFactoryProperties);
        //bean.setTypeAliasesPackage("");
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
             //bean.setMapperLocations(
            //resolver.getResources("classpath:/com/mybatis/mapper/*Mapper.xml"));
            bean.setMapperLocations(
                    resolver.getResources("classpath*:/mapper/**/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(this.appDataSource);
    }
}
