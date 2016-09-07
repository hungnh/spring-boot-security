package uet.hungnh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uet.hungnh.security.filter.AuthenticationFilter;
import uet.hungnh.security.handler.TokenClearingLogoutHandler;
import uet.hungnh.security.provider.TokenAuthenticationProvider;
import uet.hungnh.security.service.ITokenService;
import uet.hungnh.security.service.impl.InMemoryTokenService;

import static uet.hungnh.security.constants.SecurityConstant.LOGOUT_ENDPOINT;

public class TokenBaseSecurityConfig extends AbstractSecurityConfig {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(tokenAuthenticationProvider())
                .authenticationProvider(daoAuthenticationProvider())
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(publicAPIs()).permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())
                .and()
                .logout()
                .logoutUrl(LOGOUT_ENDPOINT)
                .addLogoutHandler(tokenClearingLogoutHandler())
                .logoutSuccessHandler(logoutSuccessHandler());

        http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(tokenService());
    }

    @Bean
    public ITokenService tokenService() {
        return new InMemoryTokenService();
    }

    @Bean
    public LogoutHandler tokenClearingLogoutHandler() {
        return new TokenClearingLogoutHandler();
    }

}
