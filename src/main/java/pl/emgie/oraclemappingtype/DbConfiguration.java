package pl.emgie.oraclemappingtype;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DbConfiguration {

    Environment env;

    @Autowired
    public DbConfiguration(Environment env) {
        this.env = env;
    }

    /*
    Spring is trying to close BasicDataSource twice:
    BasicDataSource close itself automatically when application close
    Spring use default destroy method to close DataSource but it's already closed
    To avoid this, use: @Bean(destroyMethod = "")
    */

    @Bean(destroyMethod = "")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPersistenceUnitName("oracleTypes");
        em.setPackagesToScan("pl.emgie.oraclemappingtype.model");
        em.setJpaProperties(hibernateProperties());
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibProp = new Properties();
        hibProp.setProperty("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        hibProp.setProperty("hibernate.show_sql", "true");
        hibProp.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.DefaultNamingStrategy");
        return hibProp;
    }

}
