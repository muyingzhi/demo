package com.example.demo.config;

import com.example.demo.security.*;
import com.example.demo.security.RestExceptionTranslationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		MyUsernamePasswordAuthenticationFilter upFilter = new MyUsernamePasswordAuthenticationFilter(tokenAuthenticationService);
		upFilter.setAuthenticationManager(this.authenticationManager());
		upFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
		upFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		http
		.httpBasic().disable()
		.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/static/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
				.permitAll().and()
		.logout()
				.addLogoutHandler(new MyLogoutHandler())
				.permitAll().and()
		.csrf()
			.disable()
//		.addFilterBefore(new MyLoginFilter("/dologin",
//						userDetailsService,
//						this.authenticationManager(),
//						tokenAuthenticationService),
//				SecurityContextHolderAwareRequestFilter.class)
				//		.addFilter(new MyLogoutFilter("/logout",new MyLogoutHandler()))
		.addFilterBefore(upFilter,UsernamePasswordAuthenticationFilter.class)
		.addFilterAfter(new RestFilter(tokenAuthenticationService), MyUsernamePasswordAuthenticationFilter.class)
//		.addFilterBefore(this.exceptionTranslationFilter(),ExceptionTranslationFilter.class)
		;
	}
	@Autowired
	private UserDetailsService userDetailsService;
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);

	}

	public ExceptionTranslationFilter exceptionTranslationFilter() {
		RestExceptionTranslationFilter exceptionTranslationFilter = new RestExceptionTranslationFilter(new MyAuthenticationEntryPoint());
		RestAccessDeniedHandler accessDeniedHandlerImpl = new RestAccessDeniedHandler();
		exceptionTranslationFilter.setAccessDeniedHandler(accessDeniedHandlerImpl);
		exceptionTranslationFilter.afterPropertiesSet();
		return exceptionTranslationFilter;
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler(){
		String failureUrl = "/login?error";
		return new MyAuthenticationFailureHandler(failureUrl);
	}
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler(){
		String successfulUrl = "/main";
		return new MyAuthenticationSuccessHandler(successfulUrl,this.tokenAuthenticationService);
	}



}
