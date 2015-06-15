//package com.taxholic.configuration.beans;
//
//import org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder;
//
//import org.jasypt.util.password.StrongPasswordEncryptor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.security.web.context.SecurityContextPersistenceFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SellerSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(ecsSellerAuthenticationProvider());
//    }
//
//    @Bean
//    public AuthenticationProvider ecsSellerAuthenticationProvider() {
//        SellerAuthenticationProvider ecsSellerAuthenticationProvider = new SellerAuthenticationProvider();
//        ecsSellerAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return ecsSellerAuthenticationProvider;
//    }
//
//    @Bean
//    public SecurityContextPersistenceFilter securityContextPersistenceFilter() {
//        SecurityContextPersistenceFilter securityContextPersistenceFilter = new SecurityContextPersistenceFilter(
//            ecsSellerCookieSecurityContextRepository());
//        return securityContextPersistenceFilter;
//    }
//
//    @Bean
//    public EcsSellerCookieSecurityContextRepository ecsSellerCookieSecurityContextRepository() {
//        return new EcsSellerCookieSecurityContextRepository();
//    }
//
//    @Bean
//    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
//
//        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new SellerAuthenticationFilter();
//        usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
//        usernamePasswordAuthenticationFilter.setUsernameParameter("id");
//        usernamePasswordAuthenticationFilter.setPasswordParameter("password");
//        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(successHandler());
//        usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(failHandler());
//        return usernamePasswordAuthenticationFilter;
//    }
//
//    @Bean
//    public AuthenticationFailureHandler failHandler() {
//        return new SimpleUrlAuthenticationFailureHandler("/login");
//    }
//
//    @Bean
//    public AuthenticationSuccessHandler successHandler() {
//        return new EcsSellerAuthenticationSuccessHandler();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilter(securityContextPersistenceFilter());
//        http.addFilter(usernamePasswordAuthenticationFilter());
//        http.authorizeRequests().anyRequest().authenticated();
//        http.csrf().disable();
//        http.formLogin().loginPage("/login").permitAll();
//        http.logout().addLogoutHandler(logoutHandler()).deleteCookies("SESS").logoutUrl("/logout").logoutSuccessUrl(
//            "/");
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // web.ignoring().antMatchers("/regist");
//        // web.ignoring().antMatchers("/registForm");
//        web.ignoring().antMatchers("/share/**");
//        web.ignoring().antMatchers("/static/**");
//        web.ignoring().antMatchers("/sample/**");
//        // web.ignoring().antMatchers("/**/login");
//        // web.ignoring().antMatchers("/**");
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        PasswordEncoder passwordEncoder = new PasswordEncoder();
//        passwordEncoder.setPasswordEncryptor(new StrongPasswordEncryptor());
//        return new PasswordEncoder();
//    }
//
//    @Bean
//    public LogoutHandler logoutHandler() {
//        return new EcsSellerLogoutHandler();
//    }
//}
