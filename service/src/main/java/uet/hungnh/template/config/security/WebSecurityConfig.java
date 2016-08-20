package uet.hungnh.template.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uet.hungnh.template.config.security.auth.*;
import uet.hungnh.template.config.security.token.TokenService;
import uet.hungnh.template.controller.APIController;

@Configuration
@EnableWebSecurity
@ComponentScan(
        basePackages = "uet.hungnh.template.config.security",
        excludeFilters = @ComponentScan.Filter({Configuration.class})
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(tokenAuthenticationProvider())
                .authenticationProvider(usernamePasswordAuthenticationProvider())
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers(apiEndpoints()).hasAuthority("ROLE_USER")
                    .antMatchers(APIController.AUTHENTICATION_ENDPOINT).hasAnyAuthority("ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())
                .and()
                    .anonymous().disable()
                    .logout()
        ;

        http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
    }

    private String[] apiEndpoints() {
        return new String[]{APIController.API_ENDPOINT};
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(tokenService());
    }

    @Bean
    public AuthenticationProvider usernamePasswordAuthenticationProvider() {
        return new UsernamePasswordAuthenticationProvider(tokenService(), usernamePasswordAuthenticationService());
    }

    @Bean
    public TokenService tokenService() {
        return new TokenService();
    }

    @Bean
    public UsernamePasswordAuthenticationService usernamePasswordAuthenticationService() {
        return new UsernamePasswordAuthenticationService();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return new UnauthorizedEntryPoint();
    }
}

// https://github.com/virgo47/restful-spring-security
// https://virgo47.wordpress.com/2014/07/27/restful-spring-security-with-authentication-token/
