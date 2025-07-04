package org.arfath.security.config;

import org.arfath.security.filter.JwtFilter;
import org.arfath.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
SecurityFilterChain
-It's the chain of filters that intercepts every HTTP request
-It applies security checks before the request reaches your controller

HttpSecurity
-It's an object provided by Spring Security
-You use it to define all your security rules

1️⃣ You configure rules using HttpSecurity
2️⃣ Call http.build() → Generates SecurityFilterChain
3️⃣ SecurityFilterChain sits in front of your endpoints
4️⃣ Every HTTP request passes through the chain
5️⃣ Chain applies your configured security logic

*/
@Configuration

public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()
                        .anyRequest()
                        .authenticated()
                ).formLogin(login -> login.disable())
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
    }

    // Configuring in-memory custom users using UserDetailsService

    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .passwordEncoder(password -> PasswordEncoder().encode(password))
                .username("arfath1")
                .password("password")
                .build();

        return new InMemoryUserDetailsManager(user1);


    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);


        return daoAuthenticationProvider;
    }

}
