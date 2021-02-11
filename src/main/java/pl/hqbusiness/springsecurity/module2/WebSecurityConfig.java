package pl.hqbusiness.springsecurity.module2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    User userAdmin = new User("admin",
        getPasswordEncoder().encode("admin123"),
        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));

    User userUser = new User("user",
        getPasswordEncoder().encode("user123"),
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

    auth.inMemoryAuthentication().withUser(userAdmin);
    auth.inMemoryAuthentication().withUser(userUser);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/hello-admin").hasRole("ADMIN")
        .antMatchers("/hello-user").hasAnyRole("ADMIN", "USER")
        .antMatchers("/hello-stranger").anonymous()
        .antMatchers("/hello-stranger").permitAll()
        .and()
        .formLogin().permitAll()
        .and()
        .logout().logoutSuccessUrl("/bye");

  }
}
