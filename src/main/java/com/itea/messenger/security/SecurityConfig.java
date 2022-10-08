package com.itea.messenger.security;

import com.itea.messenger.service.DefaultUserDetailsService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableWebSecurity
@EnableWebSecurity(debug = true)
@SecurityScheme(name="itea.messenger", scheme="basic", type= SecuritySchemeType.HTTP, in= SecuritySchemeIn.HEADER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
            // other public endpoints of your API may be appended to this array
    };

    @Autowired
    private DefaultUserDetailsService userDetailsService;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
//        TODO - change to  BCryptPasswordEncoder() for production
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(customAuthenticationProvider);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers(AUTH_WHITELIST).authenticated()
                .antMatchers(AUTH_WHITELIST).permitAll()
//                .antMatchers("/admin/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .formLogin();
    }

}
