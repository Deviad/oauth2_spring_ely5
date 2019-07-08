package com.simplicity.authserver.configs;

import com.simplicity.authserver.security.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Slf4j
@Configuration

public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private CustomUserDetailsService userDetailsService;

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/resources/**");
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests()
        //                .antMatchers("/login").permitAll()
        .antMatchers("/admin").hasRole("ADMIN")
        .antMatchers("/", "/login", "/logout")
        .permitAll()
        .and()
        .formLogin()
        .permitAll()
        .and()
        .logout()
        .logoutSuccessUrl("/").permitAll()
        .clearAuthentication(true).invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")

        //                .and()
        //                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(31536000)

        .and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//        .requireCsrfProtectionMatcher(csrfRequestMatcher);
  }


  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(encoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

//  private RequestMatcher csrfRequestMatcher = new RequestMatcher() {
//    private RegexRequestMatcher requestMatcher =
//            new RegexRequestMatcher("/oauth/*", null);
//
//    public boolean matches(HttpServletRequest httpServletRequest) {
//      if (requestMatcher.matches(httpServletRequest)) {
//        return true;
//      }
//      return false;
//    }
//  };
}
