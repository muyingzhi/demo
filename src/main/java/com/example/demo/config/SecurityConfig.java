package com.example.demo.config;

import com.example.demo.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

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
		//----登录的filter
		MyUsernamePasswordAuthenticationFilter upFilter = new MyUsernamePasswordAuthenticationFilter(tokenAuthenticationService);
		upFilter.setAuthenticationManager(this.authenticationManager());
		upFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
		upFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		//-----支持token的验证
		AuthenticationWithTokenFilter withTokenFilter =
				new AuthenticationWithTokenFilter("/user/**");
		withTokenFilter.setTokenService(tokenAuthenticationService);
		//-----
		FilterSecurityInterceptor securityInterceptor = new MyFilterSecurityInterceptor(new AntPathRequestMatcher("/user/**"));
//		securityInterceptor.setSecurityMetadataSource(new DefaultFilterInvocationSecurityMetadataSource());
		http
		.httpBasic().disable()
		.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/*.html").permitAll()
				.antMatchers("/static/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin().loginPage("/login")
				.permitAll().and()
		.logout().logoutUrl("/logout")
				.logoutSuccessUrl("/")
		.addLogoutHandler(new MyLogoutHandler())
				.permitAll().and()
		.exceptionHandling().defaultAuthenticationEntryPointFor(
				new AjaxAuthenticationEntryPoint(),
				new AjaxRequestMatcher())
				.and().authorizeRequests().antMatchers("/user/**").hasRole("user").and()
		.csrf().disable()
		.addFilterBefore(upFilter,UsernamePasswordAuthenticationFilter.class)
		.addFilterAfter(withTokenFilter, SecurityContextHolderAwareRequestFilter.class)
		.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class)
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
