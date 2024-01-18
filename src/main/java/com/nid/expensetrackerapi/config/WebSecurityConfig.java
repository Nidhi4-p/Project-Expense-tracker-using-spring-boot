package com.nid.expensetrackerapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nid.expensetrackerapi.security.CustomUserDetailsService;
import com.nid.expensetrackerapi.security.JwtRequestFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailsService customUser;
	
	@Bean
	public JwtRequestFilter authenticationJwtTokenFilter(){
		return new JwtRequestFilter();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/login","/register").permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class)
		.httpBasic();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//		.inMemoryAuthentication()
//		.withUser("Nidhi").password("12345").authorities("admin")
//		.and()
//		.withUser("Smitha").password("12345").authorities("user")
//		.and()
//		.passwordEncoder(NoOpPasswordEncoder.getInstance());
		
//		InMemoryUserDetailsManager userdetailsmanager = new InMemoryUserDetailsManager();
//		UserDetails user1 = User.withUsername("Gokul").password("1432").authorities("user").build();
//		UserDetails user2 = User.withUsername("Pradeep").password("12567").authorities("user").build();
//		userdetailsmanager.createUser(user1);
//		userdetailsmanager.createUser(user2);
		auth.userDetailsService(customUser);
		}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	
		return super.authenticationManagerBean();
	}
		
}
