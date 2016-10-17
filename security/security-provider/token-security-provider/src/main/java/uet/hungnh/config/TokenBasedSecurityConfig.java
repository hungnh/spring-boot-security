package uet.hungnh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uet.hungnh.security.filter.TokenAuthenticationFilter;
import uet.hungnh.security.filter.UsernamePasswordAuthenticationFilter;
import uet.hungnh.security.handler.TokenClearingLogoutHandler;
import uet.hungnh.security.provider.TokenAuthenticationProvider;
import uet.hungnh.security.service.ITokenService;
import uet.hungnh.security.service.impl.InMemoryTokenService;
import uet.hungnh.security.service.impl.PersistedTokenService;

import static uet.hungnh.security.constants.SecurityConstant.LOGOUT_ENDPOINT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(
        basePackages = "uet.hungnh.security",
        excludeFilters = @ComponentScan.Filter({Configuration.class})
)
@PropertySource("classpath:/security.properties")
public class TokenBasedSecurityConfig extends AbstractSecurityConfig {

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

        http.addFilterBefore(new UsernamePasswordAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
        http.addFilterAfter(new TokenAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(tokenService());
    }

    @Bean
    public ITokenService tokenService() {
        return new PersistedTokenService();
    }

    @Bean
    public LogoutHandler tokenClearingLogoutHandler() {
        return new TokenClearingLogoutHandler();
    }

}
