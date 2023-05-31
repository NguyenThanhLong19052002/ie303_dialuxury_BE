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

    import  com.ie303.dialuxury.service.userServiceImpl;

    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
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
                    .antMatchers("/").permitAll()
                    .antMatchers("/user/signup").permitAll()
                    .antMatchers("/user/login").permitAll()
                    .antMatchers("/product").permitAll()
                    .antMatchers("/product/{_id}").permitAll()
                    .antMatchers("/cart").permitAll()
                    .antMatchers("/cart/add").permitAll()
                    .antMatchers("/cart/update").permitAll()
                    .antMatchers("/cart/remove/{cartItemId}").permitAll()
                    .antMatchers("/cart/clear").permitAll()
                    .anyRequest().authenticated();
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
    }
