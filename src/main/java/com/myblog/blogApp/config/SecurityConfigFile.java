package com.myblog.blogApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfigFile extends WebSecurityConfigurerAdapter {

    //Ctrl + O and override the method and give the @BEAN ANNOTATION
    //11-> after that it will give an OBJECT and this obj address is inject to AUTHCONTROLLER
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncod(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/**").permitAll()

                //<- <- <- <-<- <- <- <-<- <-SIGN IN SHOULD HAVE PERMIT TO ALL <- <-<- <- <- <-<- <- <- <-
                .antMatchers("/api/auth/**").permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

//    @Override
//    protected UserDetailsService userDetailsService() {
//
////        UserDetails custom = User.builder().username("abhisek565@gmail.com").password("Abhisek565@").roles("CUSTOM").build();
////        UserDetails admin = User.builder().username("admin@gmail.com").password("Admin@").roles("ADMIN").build();
//
//        //<- -> <- -> <- -> <- -> <- -> <- -> <- -> <- -> <- -> <- -> <- -> <- -> <- -> <- -> <- -> <- -> <- -> <- ->
//        UserDetails user = User.builder().username("pankaj")
//                .password(passwordEncod().encode("password")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin")
//                .password(passwordEncod().encode("password")).roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(user,admin);
//    }
}
