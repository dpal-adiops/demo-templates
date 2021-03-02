package com.fafunda.authms.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fafunda.authms.security.jwt.filter.AuthRequestFilter;
import com.fafunda.authms.security.service.JpaUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	@Autowired
	private JpaUserDetailService profileUserDetailService;

	@Autowired
	private AuthRequestFilter jwtRequestFilter;
	
  	@Autowired
	private AppAuthEntryPoint jwtAuthenticationEntryPoint;

	protected void configure(HttpSecurity http) throws Exception {
		
		  http  
	         .authorizeRequests()
	         .antMatchers("/").permitAll()
		 	 .antMatchers("/v1/token").permitAll()
		 	 .antMatchers("/v1/signup").permitAll()
		 	 .antMatchers("/v1/users").hasAnyAuthority("ADMIN")
	         .anyRequest().fullyAuthenticated() 
	         .and()
	         .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
	         .exceptionHandling()
	                  .authenticationEntryPoint(jwtAuthenticationEntryPoint)
	         .and()
	         .httpBasic()
	         .and()
	         .logout()
	             .logoutUrl("/logout")
	             .logoutSuccessHandler(logoutSuccessHandler())
	             .permitAll()
	         .and()
	         .sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			 .and()			 
	         .csrf().disable();

	}

	  
  	private LogoutSuccessHandler  logoutSuccessHandler ()  { 
  		return  new  HttpStatusReturningLogoutSuccessHandler (); 
  	}
  
  	@Bean
 	public CorsConfigurationSource corsConfigurationSource() {
  		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
  		return source;
  	}
  	

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("$2a$15$pylT47G2OpP0QRsMYkXG.uELLvc0UKc4AEymViXF3sNNOuNv9TXkG").roles("ADMIN");
		auth.userDetailsService(profileUserDetailService).passwordEncoder(passwordEncoder());
	}
	
	


}
