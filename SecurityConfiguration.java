//package com.finalWebSpring.Configuration;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.stereotype.Controller;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
//	
//	@Autowired
//	DataSource dataSource;
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(dataSource)
//		.usersByUsernameQuery("SELECT username, password, enable FROM user WHERE username = ?")
//		.authoritiesByUsernameQuery("SELECT username, role FROM user WHERE username = ?");
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
//		.and().authorizeRequests().antMatchers("/user/**").hasRole("USER")
//		.anyRequest().permitAll().and().formLogin().loginPage("/dang-nhap").loginProcessingUrl("/dang-nhap")
//		.usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/admin-page")
//		.failureUrl("/dang-nhap?error=failed").and().exceptionHandling().accessDeniedPage("/dang-nhap?error=deny");
//		
//	}
//	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/static/**", "/files/**");
//	}
//}
