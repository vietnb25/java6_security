package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder getbCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("user1").password(bCryptPasswordEncoder.encode("123")).roles("GUEST")
                .and()
                .withUser("user2").password(bCryptPasswordEncoder.encode("123")).roles("USER","GUEST")
                .and()
                .withUser("user3").password(bCryptPasswordEncoder.encode("123")).roles("ADMIN","USER","GUEST");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //CSRF, CORS
        http.csrf().disable().cors().disable();

        //PHÂN QUYỀN
        http.authorizeRequests()
                .antMatchers("home/index").permitAll()
                .anyRequest().authenticated();

        //GIAO DIỆN
        http.httpBasic();
    }
}
