package com.javacodegeeks.examples.security;

import com.javacodegeeks.examples.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import javax.inject.Inject;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

	@Autowired
	private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

	@Autowired
	private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

	@Autowired
	private Http401UnauthorizedEntryPoint authenticationEntryPoint;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
	http
/*			.csrf()
			.and()
			.addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)*/
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.formLogin()
			.loginProcessingUrl("/api/authentication")
			.successHandler(ajaxAuthenticationSuccessHandler)
			.failureHandler(ajaxAuthenticationFailureHandler)
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll()
			.and()
			.logout()
			.logoutUrl("/api/logout")
			.logoutSuccessHandler(ajaxLogoutSuccessHandler)
			.deleteCookies("JSESSIONID")
			.permitAll()
			.and()
			.authorizeRequests()
			.antMatchers("/api/**").authenticated();



	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
        userDetailsService.setDataSource(dataSource);
        auth.userDetailsService(userDetailsService).passwordEncoder(Util.getEncoder());
        auth.jdbcAuthentication().dataSource(dataSource);        
	}

}


