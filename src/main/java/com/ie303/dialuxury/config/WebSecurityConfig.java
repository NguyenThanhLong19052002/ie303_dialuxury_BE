package com.ie303.dialuxury.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;


import com.ie303.dialuxury.service.userServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private userServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //        các api không cần đăng nhập
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/product").permitAll()
                .antMatchers("/product/{_id}").permitAll()
                .antMatchers("/user/{userId}").permitAll()
                .antMatchers("/cart").permitAll()
                .antMatchers("/cart/add").permitAll()
                .antMatchers("/cart/update").permitAll()
                .antMatchers("/cart/remove/{cartItemId}").permitAll()
                .antMatchers("/cart/clear").permitAll()
                .antMatchers("/user/{userId}/change-password").permitAll()
                .antMatchers("/user/{email}/reset").permitAll()
                .antMatchers("/user/{email}/forgot").permitAll()
                .antMatchers("/user/{email}/recovery").permitAll()
                .antMatchers("/admin/user/count").permitAll()
                .antMatchers("/admin/order/count").permitAll()
                .antMatchers("/admin/product/count").permitAll()
                .antMatchers("/admin/revenue").permitAll()
                .antMatchers("/admin/user").permitAll()
                .anyRequest().authenticated()
                .and().cors();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // Cho phép truy cập từ bất kỳ nguồn gốc nào
        configuration.addAllowedMethod("*"); // Cho phép tất cả các phương thức (GET, POST, PUT, DELETE, vv.)
        configuration.addAllowedHeader("*"); // Cho phép tất cả các header
        configuration.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng cấu hình CORS cho tất cả các URL
        return source;
    }
}
