package com.derekdileo.expensetrackerrestapi.config;

import com.derekdileo.expensetrackerrestapi.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// WebSecurityConfigurerAdapter is deprecated. How do we resolve this?
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // Runtime constructor-based injection of CustomUserDetailsService dependency
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

// Code to manually configure multiple users and code to
// (possibly) replace deprecated WebSecurityConfigurerAdapter
/*
*
*
* //    // Second method to configure multiple users
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//
//        UserDetails user1 = User.withUsername("derek").password("password").authorities("admin").build();
//        UserDetails user2 = User.withUsername("chris").password("password").authorities("user").build();
//
//        userDetailsService.createUser(user1);
//        userDetailsService.createUser(user2);
//
//        auth.userDetailsService(userDetailsManager);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }


//    // First Method to configure multiple users
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication()
//                .withUser("derek").password("password").authorities("admin")
//                .and()
//                .withUser("chris").password("password").authorities("user")
//                .and()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }
*
*
*
*
* // Options to resolve deprecated WebSecurityConfigurerAdapter Interface
*
*     @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/register", "login").permitAll()
                        .anyRequest().authenticated()
                        .and()
                )
                .httpBasic();
        // @formatter:on

        return http.build();
    }
*
*
*
*
* @Bean
SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	return http
			.antMatcher("/**")
			.authorizeRequests(authorize -> authorize
					.anyRequest().authenticated()
			)
			.build();
}
Current Behavior

The equivalent configuration by extending the WebSecurityConfigurerAdapter looks like this

@Configuration
static class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/**")
			.authorizeRequests(authorize -> authorize
					.anyRequest().authenticated()
			);
	}
}
*
*
*
* */