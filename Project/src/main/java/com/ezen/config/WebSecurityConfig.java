package com.ezen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http.csrf().disable()
               .authorizeRequests()
               .antMatchers("/user/**").authenticated()
               .antMatchers("/admin/**").hasRole("ADMIN")
               .anyRequest().permitAll()
               .and()
               
               .formLogin()
               .loginPage("/login")
               .loginProcessingUrl("/loginProc")
               .defaultSuccessUrl("/loginSuccess")
               .failureUrl("/login?error=true") // 로그인 실패 시 이동할 URL
               .and()
               
               .logout()
               .logoutUrl("/logout")
               .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 URL
               .invalidateHttpSession(true)
               .and()
               .build();
   }
   
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}