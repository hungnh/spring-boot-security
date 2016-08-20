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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uet.hungnh.template.config.security.auth.AuthenticationFilter;
import uet.hungnh.template.config.security.auth.UnauthorizedEntryPoint;
import uet.hungnh.template.config.security.provider.TokenAuthenticationProvider;
import uet.hungnh.template.config.security.provider.UsernamePasswordAuthenticationProvider;
import uet.hungnh.template.config.security.service.TokenService;
import uet.hungnh.template.config.security.service.UsernamePasswordAuthenticationService;
import uet.hungnh.template.controller.APIController;

import static uet.hungnh.template.config.security.constants.SecurityConstants.ALL_ROLES;
import static uet.hungnh.template.config.security.constants.SecurityConstants.ROLE_USER;

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
                    .antMatchers(apiEndpoints()).hasAuthority(ROLE_USER)
                    .antMatchers(APIController.AUTHENTICATION_ENDPOINT).hasAnyAuthority(ALL_ROLES)
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
