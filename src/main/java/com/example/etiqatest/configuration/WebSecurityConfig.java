package com.example.etiqatest.configuration;


import com.example.etiqatest.ErrorHandler.CustomAuthenticationFailureHandler;
import com.example.etiqatest.entity.Roles;
import com.example.etiqatest.principle.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authProvider(){
        DaoAuthenticationProvider provider =new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // We call the service and the service will call the database
        provider.setPasswordEncoder(new BCryptPasswordEncoder()); // password later to be configured for encryption
        return provider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( HttpMethod.GET,"/index*","/resources/**", "/static/**","/css/**", "/*.js", "/*.json", "/*.ico","/getUserIdRole","/logout")
                .permitAll()
              
                .anyRequest().authenticated()//
                .and()
                .formLogin()
                .loginPage("/index.html")//customize login page url
                .loginProcessingUrl("/log")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        if (authentication != null){
                            UserPrinciple userPrinciple= (UserPrinciple) authentication.getPrincipal();
                            Set<Roles> getRoles= userPrinciple.getRoles();
                            request.setAttribute("roles", String.valueOf(getRoles));
                            request.setAttribute("id", String.valueOf(userPrinciple.getId()));
                        }
                        response.setContentType("application/json; charset=UTF-8");
                        response.sendRedirect("/getUserIdRole");
                        //response.sendRedirect("/homepage.html");
                    }
                })
                .failureUrl("/index.html?error=true")
                .failureHandler(customAuthenticationFailureHandler())//Handles the error message for invalid username and locked user
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/logout")//this will call the url after logout
                .invalidateHttpSession(true)//invalidate the session
                .clearAuthentication(true)//clear all the authentication
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")//when this url is called, the logout will act
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/logout");
    }


    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }


}
