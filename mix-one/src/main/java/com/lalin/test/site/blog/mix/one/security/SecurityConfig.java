package com.lalin.test.site.blog.mix.one.security;


import com.lalin.test.site.blog.mix.one.Dao.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Created by frzhao on 2017/4/10.
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired
    public MyUserDetailService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers("/index","/home").permitAll()
                        .antMatchers("/static/**").permitAll()
                        .antMatchers("/borrowList").permitAll()
                        .antMatchers("/admin/**").hasAnyRole("ADMIN")
                        .antMatchers("/probeList/**").hasAnyRole( "ADMIN")
                        .antMatchers("/haveLog").hasAnyRole("USER", "ADMIN")
                //        .anyRequest().authenticated()
                        .and()
                .formLogin()
                        .loginPage("/login").permitAll()
                        .failureUrl("/login-error").permitAll();
     //   http.csrf().disable();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      //  auth.inMemoryAuthentication().withUser("sss").password("password").roles("USER");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        //	auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authProvider);
    }
}
