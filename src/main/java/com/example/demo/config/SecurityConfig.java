package com.example.demo.config;

import com.example.demo.auth.service.UserService;
import com.example.demo.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;
	@Autowired
	private FilterInvocationSecurityMetadataSource metadataSource;
	@Autowired
	private AccessDecisionManager accessDecisionManager;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		OAuth2AuthenticationProcessingFilter oauth2Filter = new OAuth2AuthenticationProcessingFilter();
		oauth2Filter.setAuthenticationManager(this.authenticationManager());


		//----认证filter
		MyAuthenticationFilter authenticationFilter = new MyAuthenticationFilter(tokenAuthenticationService);
		authenticationFilter.setAuthenticationManager(this.authenticationManager());
		authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
		authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		//-----支持token的验证
		MyAuthenticationWithTokenFilter withTokenFilter =
				new MyAuthenticationWithTokenFilter("/user/**");
		withTokenFilter.setTokenService(tokenAuthenticationService);
		//-----
		FilterSecurityInterceptor securityInterceptor =
				new MyFilterSecurityInterceptor(new AntPathRequestMatcher("/user/**"),
						metadataSource,
						accessDecisionManager);
//		securityInterceptor.setSecurityMetadataSource(new DefaultFilterInvocationSecurityMetadataSource());
		http
		.httpBasic().disable()
		.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/*.html").permitAll()
				.antMatchers("/static/**").permitAll()
			.anyRequest().authenticated()
			.and()
//		.formLogin().loginPage("/login")
//				.permitAll().and()
		.logout().logoutUrl("/logout")
				.logoutSuccessUrl("/")
		.addLogoutHandler(new MyLogoutHandler())
				.permitAll().and()
		.exceptionHandling().defaultAuthenticationEntryPointFor(// ajax请求的异常处理
				new AjaxAuthenticationEntryPoint(),
				new AjaxRequestMatcher())
		.and().authorizeRequests()
		.and().csrf().disable()
//		.addFilterBefore(oauth2Filter,UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class)
		.addFilterAfter(withTokenFilter, SecurityContextHolderAwareRequestFilter.class)
		.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class)
		;
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public UserDetailsService userDetailsService(){
		return new UserService();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());

	}
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler(){
		String failureUrl = "/login?error";//加入error是为login.html处理异常而用
		return new MyAuthenticationFailureHandler(failureUrl);
	}
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler(){
		String successfulUrl = "/main";
		return new MyAuthenticationSuccessHandler(successfulUrl,this.tokenAuthenticationService);
	}
}
