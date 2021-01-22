package de.othr.sw.benjamineder.barmanagement.application.auth.config;

import de.othr.sw.benjamineder.barmanagement.application.auth.provider.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

  private final AuthProvider authProvider;

  @Autowired
  public AuthConfiguration(AuthProvider authProvider) {
    this.authProvider = authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/api/**").permitAll()
        .antMatchers("/styles/**").permitAll()
        .antMatchers("/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/")
        .failureUrl("/login?error=true")
        .and()
        .logout()
        .logoutUrl("/logout")
        .deleteCookies("JSESSIONID");

    http.cors()
        .and()
        .csrf()
        .ignoringAntMatchers("/api/**");
  }
}
