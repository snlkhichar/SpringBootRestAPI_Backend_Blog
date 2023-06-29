package com.codewithdurgesh.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithdurgesh.blog.security.CustomUserDetailService;
import com.codewithdurgesh.blog.security.JwtAuthenticationEntryPoint;
import com.codewithdurgesh.blog.security.JwtAuthenticationFilter;
//6.JWT token related step configure JWT in spring security config
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	public static final String[] PUBLIC_URLS= {
		"/api/v1/auth/**",
		"/v3/api-docs",
		"/v2/api-docs",
		"/swagger-resources/**",
		"/swagger-ui/**",
		"/webjars/**"
			
	};
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	//Authentication Info (AuthN)
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			System.out.println("configure method AuthenticationManagerBuilder");
			auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
			
		}

	//Authorization info (AuthZ)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("configure method HttpSecurity");
		//Basic Authentication
		//http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
		
		//configure JWT in spring Security
		http.csrf().disable()
		//.authorizeRequests().antMatchers("/api/v1/auth/login").permitAll()
		.authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
		.antMatchers(HttpMethod.GET).permitAll()
		.anyRequest()
		.authenticated().
		and().exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}


	@Bean
	public PasswordEncoder passwordEncoder()
	{
		System.out.println("passwordEncoder method");
		return new BCryptPasswordEncoder();
	}

	//Authenticate password for we need AuthenticationManager 
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
	
	
	
}
