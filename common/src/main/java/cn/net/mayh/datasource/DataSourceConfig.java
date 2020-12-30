package cn.net.mayh.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 数据库连接池配置类
 *
 * @author mayh
 * @date 2020/12/26
 **/
@Configuration
public class DataSourceConfig {

    private DbConfigProperties dbConfigProperties;

    public DataSourceConfig(DbConfigProperties dbConfigProperties){
        this.dbConfigProperties = dbConfigProperties;
    }

    /**
     * Description: 注册DruidServlet 启用数据源的Web监控统计功能
     *
     * @return : org.springframework.boot.web.servlet.ServletRegistrationBean<com.alibaba.druid.support.http.StatViewServlet>
     * @date 2020/12/26
     * @author mayh
     **/
    @Bean
    @Profile({"local", "dev", "int", "uat", "test"})
    public ServletRegistrationBean<StatViewServlet> druidServletRegistrationBean() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");

        /* 初始化参数配置，initParams */
        //白名单，多个ip逗号隔开
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        //bean.addInitParameter("deny", "192.168.1.73");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * Description: 注册DruidFilter拦截
     *
     * @return : org.springframework.boot.web.servlet.FilterRegistrationBean<com.alibaba.druid.support.http.WebStatFilter>
     * @date 2020/12/26
     * @author mayh
     **/
    @Bean
    @Profile({"local", "dev", "int", "uat", "test"})
    public FilterRegistrationBean<WebStatFilter> duridFilterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<String, String>();
        // 设置忽略请求
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    /**
     * 测试环境可以用 DruidDataSource 以可视化观察应用sql 的执行状况
     *
     * @return
     */
    @Profile({"local", "dev", "int", "uat", "test"})
    @Bean(name = "appDataSource")
    @Primary
    public DataSource appDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(this.dbConfigProperties.getJdbc_url());
        dataSource.setUsername(this.dbConfigProperties.getJdbc_username());
        dataSource.setPassword(this.dbConfigProperties.getJdbc_password());
        dataSource.setDriverClassName(this.dbConfigProperties.getJdbc_driverClass());
        dataSource.setTestOnReturn(this.dbConfigProperties.isTestOnReturn());
        dataSource.setTestWhileIdle(this.dbConfigProperties.isTestWhileIdle());
        dataSource.setTestOnBorrow(this.dbConfigProperties.isTestOnBorrow());
        dataSource.setInitialSize(this.dbConfigProperties.getInitialSize());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(this.dbConfigProperties.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setMaxActive(this.dbConfigProperties.getMaxActive());
        dataSource.setValidationQuery(this.dbConfigProperties.getValidationQuery());
        return dataSource;
    }

    /**
     * Description: 生产环境选择性能更高的HikariCP https://github.com/brettwooldridge/HikariCP
     *
     * @return : javax.sql.DataSource
     * @date 2020/12/26
     * @author mayh
     **/
    @Profile({"prd"})
    @Bean(name = "appDataSource")
    @Primary
    @Autowired
    public DataSource hikariDataSource(DbConfigProperties dbConfigProperties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.dbConfigProperties.getJdbc_url());
        config.setUsername(this.dbConfigProperties.getJdbc_username());
        config.setPassword(this.dbConfigProperties.getJdbc_password());
        config.setDriverClassName(this.dbConfigProperties.getJdbc_driverClass());
        config.setConnectionTestQuery(this.dbConfigProperties.getValidationQuery());
        config.setMaximumPoolSize(
                this.dbConfigProperties.getMaxPoolPreparedStatementPerConnectionSize());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Profile({"autoTest"})
    @Bean(name = "appDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource testDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean(name = "appJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
