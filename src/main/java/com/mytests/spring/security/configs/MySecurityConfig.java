package com.mytests.spring.security.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * *
 * <p>Created by irina on 31.05.2021.</p>
 * <p>Project: spring-security-auditing</p>
 * *
 */
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("irina").password(passwordEncoder().encode("jolt")).roles("PARENT","ADMIN").and()
                .withUser("andrey").password(passwordEncoder().encode("jolt")).roles("PARENT").and()
                .withUser("admin").password(passwordEncoder().encode("jolt")).roles("ADMIN").and()
                .withUser("vera").password(passwordEncoder().encode("jolt")).roles("CHILD");
    }
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // no url paths references here - https://youtrack.jetbrains.com/issue/IDEA-269947 - fixed
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/").fullyAuthenticated()
                .antMatchers("/for_adults").hasRole("PARENT")
                // no url paths references https://youtrack.jetbrains.com/issue/IDEA-269857 - fixed
                // no roles and authorities references https://youtrack.jetbrains.com/issue/IDEA-269955 - fixed
                .mvcMatchers("/home").hasAnyAuthority("ROLE_PARENT","ROLE_CHILD","ROLE_ADMIN")
                // no regexp autoinjected - https://youtrack.jetbrains.com/issue/IDEA-269970 - fixed
                .regexMatchers("/secret","/secure\\d+").hasAnyRole("ADMIN")
                // no url paths references - nothing is submitted yet, not sure we need to support this case
                .requestMatchers(new AntPathRequestMatcher("/for_all")).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();


        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
