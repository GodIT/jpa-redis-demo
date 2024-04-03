package com.kim.jparedisdemo;

import com.kim.jparedisdemo.dao.MyHibernateDaoSupport;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@EntityScan("com.kim.jparedisdemo.model") //
@EnableCaching
public class JpaRedisDemoApplication {


    @Value("${spring.hibernate.dialect}")
    private String dialect;

    @Value("${spring.hibernate.format_sql}")
    private String formatSql;

    @Value("${spring.hibernate.show_sql}")
    private String showSql;

    @Value("${spring.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${spring.hibernate.max_fetch_depth}")
    private String maxFetchDepth;

    @Value("${spring.hibernate.jdbc.fetch_size}")
    private String fetchSize;

    @Value("${spring.hibernate.jdbc.batch_size}")
    private String batchSize;

    @Value("${spring.hibernate.jdbc.use_scrollable_resultset:true}")
    private String useScrollableResultset;

    @Value("${spring.hibernate.cglib.use_reflection_optimizer:true}")
    private String useReflectionOptimizer;

    @Value("${spring.hibernate.cache.use_second_level_cache:false}")
    private String useSecondLevelCache;

    @Value("${spring.hibernate.cache.use_query_cache:false}")
    private String useQueryCache;

    @Value("${spring.hibernate.jdbc.lob.non_contextual_creation:false}")
    private String nonContextualCreation;

    @Value("${spring.hibernate.default_schema:}")
    private String defaultSchema;

    public static void main(String[] args) {
        SpringApplication.run(JpaRedisDemoApplication.class, args);
    }


//    @Primary
//    @Bean
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory);
//        return transactionManager;
//    }

//    @Bean
    public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
        return hibernateTemplate;
    }

    //    @Bean("sessionFactory")
//    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
//        return this.buildSessionFactoryBy(dataSource);
//    }

//    @Bean
    public SessionFactory sessionFactory(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        return emf.unwrap(SessionFactory.class);
    }

//    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

//    @Bean
    public MyHibernateDaoSupport myHibernateDaoSupport(HibernateTemplate hibernateTemplate) {
        MyHibernateDaoSupport commonDao = new MyHibernateDaoSupport();
        commonDao.setHibernateTemplate(hibernateTemplate);
        commonDao.setSessionFactory(hibernateTemplate.getSessionFactory());
        return commonDao;
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
//        return (LocalContainerEntityManagerFactoryBean) builder
//                .withDataSource(dataSource)
//                .build();
//    }

    private LocalSessionFactoryBean buildSessionFactoryBy(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.kim.**.model.**.");

//        sessionFactory.setImplicitNamingStrategy(new ThornNamingStrategyImpl());
        Properties hibernateProperties = this.generateHibernateProperties();
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    private Properties generateHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", this.dialect);
        properties.setProperty("hibernate.format_sql", this.formatSql);
        properties.setProperty("hibernate.show_sql", this.showSql);
        properties.setProperty("hibernate.hbm2ddl.auto", this.hbm2ddlAuto);
        properties.setProperty("hibernate.max_fetch_depth", this.maxFetchDepth);
        properties.setProperty("hibernate.jdbc.fetch_size", this.fetchSize);
        properties.setProperty("hibernate.jdbc.batch_size", this.batchSize);
        properties.setProperty("hibernate.jdbc.use_scrollable_resultset", this.useScrollableResultset);
        properties.setProperty("hibernate.cglib.use_reflection_optimizer", this.useReflectionOptimizer);
        properties.setProperty("hibernate.cache.use_second_level_cache", this.useSecondLevelCache);
        properties.setProperty("hibernate.cache.use_query_cache", this.useQueryCache);
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", this.nonContextualCreation);
        if (!StringUtils.isEmpty(defaultSchema)) {
            properties.setProperty("hibernate.default_schema", this.defaultSchema);
        }
        return properties;
    }

}
