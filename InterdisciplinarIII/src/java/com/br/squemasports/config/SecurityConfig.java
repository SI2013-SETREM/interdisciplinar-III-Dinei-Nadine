
package com.br.squemasports.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//http://docs.spring.io/spring-security/site/docs/4.0.1.RELEASE/guides/html5/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
//    @Autowired
//    AuthenticationUserDetails userDetails;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth//.userDetailsService(userDetails)
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                ;
    }
    
}
