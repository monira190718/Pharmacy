package com.sparktech.finalproject;


import com.sparktech.finalproject.registration.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;



    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            CustomUserDetailsService userDetailsService
    ) throws Exception {


        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);


        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);


        return auth.build();

    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {


        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/login",
                                "/registration",
                                "/registration.css",
                                "/login.css",
                                "/image/**"
                        )
                        .permitAll()


                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")


                        .requestMatchers("/customer/**")
                        .hasRole("CUSTOMER")


                        .anyRequest()
                        .authenticated()

                )


                .formLogin(login -> login

                        .loginPage("/login")

                        .loginProcessingUrl("/login")
                       .usernameParameter("username")


                       .passwordParameter("password")

                        .failureUrl("/login?error=true")


                        .successHandler(
                                (request,response,authentication)->{


                                    String role =
                                            authentication.getAuthorities()
                                                    .iterator()
                                                    .next()
                                                    .getAuthority();



                                    if(role.equals("ROLE_ADMIN")){

                                        response.sendRedirect("/admin");

                                    }


                                    else if(role.equals("ROLE_CUSTOMER")){

                                        response.sendRedirect("/customer");

                                    }


                                }
                        )


                        .permitAll()

                )
                
                .logout(logout -> logout

                .logoutUrl("/logout")

                .logoutSuccessUrl("/login?logout=true")

                .invalidateHttpSession(true)

                .clearAuthentication(true)

                .deleteCookies("JSESSIONID")

        );

        return http.build();

    }


}