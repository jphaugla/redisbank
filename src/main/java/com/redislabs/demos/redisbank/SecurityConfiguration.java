package com.redislabs.demos.redisbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment env;
    @Autowired
    private @Value("{spring.redis.user}")
    String redisUser;
    @Autowired
    private @Value("{spring.redis.password}")
    String redisPassword;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/auth-login.html").permitAll()
        .antMatchers("/assets/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/auth-login.html")
        .loginProcessingUrl("/perform_login")
        .defaultSuccessUrl("/index.html")
        .failureUrl("/auth-login.html?error=true");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception   {
        auth.inMemoryAuthentication()
        .withUser("lars")
        .password("{noop}larsje")
        .roles("USER");
    }


}
