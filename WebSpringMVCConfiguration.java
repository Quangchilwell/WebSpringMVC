package com.finalWebSpring.Configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Controller
@EnableWebMvc
@ComponentScan(basePackages = "com.finalWebSpring") // Scan all cac pack bat dau voi com.trungtamjava
@PropertySource(value = {"classpath:db.properties"})
@EnableTransactionManagement
public class WebSpringMVCConfiguration extends WebMvcConfigurerAdapter{
	
	@Autowired
	Environment environment;
	
	// Doc properties cua file
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	// Su dung tile cho jsp
	@Bean
	public static TilesConfigurer tilesConfigurer()
	{
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions("classpath:tiles.xml");
		configurer.setCheckRefresh(true);
		return configurer;
	}
	
	// Hien thi file jsp
	@Bean
	public ViewResolver viewResolver() {
		TilesViewResolver viewResolver = new TilesViewResolver();
		return viewResolver;
	}
	
	// Su dung static resource
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/files/**").addResourceLocations("file:/C:/Users/my laptop/Desktop/testFile/");
	}
	
	// Dua file vao ctr
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(-1); // Ko gioi han dung luong
		return new CommonsMultipartResolver();
	}
	
	// Cau hinh Validate
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
		// classpath : tro truc tiep vao file resources
		bundleMessageSource.setBasename("classpath:message");
		bundleMessageSource.setDefaultEncoding("utf-8");
		return bundleMessageSource;
	}
	
	// Cau hinh tao csdl
	@Bean
	public DataSource dataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/Springbh");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		return dataSource;
	}
	
	// Cau hinh su daung hibernate thao tac voi csdl
	@Bean
	public LocalSessionFactoryBean sessionFactoryBean()
	{
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setDataSource(dataSource());
		bean.setPackagesToScan("com.finalWebSpring.Entity");
		
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		hibernateProperties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
		
		bean.setHibernateProperties(hibernateProperties);
		
		return bean;
	}
	
	@Bean(name = "transactionManager")
	@Autowired
	public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setSessionFactory(sessionFactory);
		
		return hibernateTransactionManager;
	}

}
