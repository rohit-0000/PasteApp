package com.pasteApp.paste.Config;

import com.pasteApp.paste.Filter.JwtFilter;
import com.pasteApp.paste.Services.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.spi.InetAddressResolverProvider;

@Configuration
@EnableWebSecurity
public class SpringSecurity  {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .cors(Customizer.withDefaults())
                        .authorizeHttpRequests(requests->requests
                        .requestMatchers("/paste/**","/user/**").authenticated()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().permitAll()
        )

//                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    /*
    Purpose: This method configures the security filter chain, which defines how security is applied to different HTTP requests.

    Details:
        authorizeHttpRequests: Defines the authorization rules for incoming HTTP requests.
        requestMatchers("/paste/**", "/user/**").authenticated(): These endpoints require the user to be authenticated.
        requestMatchers("/admin").hasRole("ADMIN"): This endpoint requires the user to have the ADMIN role.
        anyRequest().permitAll(): All other requests are allowed without authentication.
        httpBasic(Customizer.withDefaults()): Enables HTTP Basic Authentication with default settings.
        csrf(AbstractHttpConfigurer::disable): Disables Cross-Site Request Forgery (CSRF) protection.
        build(): Builds the SecurityFilterChain object.
*/
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailServiceImp).passwordEncoder(passwordEncoder());
    }
    /*
    Purpose: This method configures the authentication manager, which is responsible for authenticating users.

        Details:
        auth.userDetailsService(userDetailServiceImp): Uses the custom UserDetailServiceImp to load user-specific data.
        passwordEncoder(passwordEncoder()): Sets the password encoder to BCryptPasswordEncoder, ensuring that passwords are encoded before being stored or compared.
*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /*
    Purpose: This method provides a password encoder bean.

        Details:
        BCryptPasswordEncoder: Uses the BCrypt hashing algorithm to encode passwords, which is a secure way to store passwords.
    */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}
