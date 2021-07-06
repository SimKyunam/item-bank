package com.example.itembank.base.config;

import com.example.itembank.base.filters.JwtAuthenticationFilter;
import com.example.itembank.base.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.key}")
    private String secret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter filter = new JwtAuthenticationFilter(
                authenticationManager(), jwtUtil());

        http
            .cors().and().csrf().disable()
            .formLogin().disable()
            .authorizeRequests()
            .antMatchers(
                    "/exception/**"
                    , "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**"
            ).permitAll()
            .anyRequest().authenticated()
//            .and()
//            .headers().frameOptions().disable()
            .and()
            .addFilter(filter)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(secret);
    }
}