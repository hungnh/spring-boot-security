package uet.hungnh.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uet.hungnh.security.filter.JwtAuthenticationFilter;
import uet.hungnh.security.filter.UsernamePasswordAuthenticationFilter;
import uet.hungnh.security.provider.JwtAuthenticationProvider;

import static uet.hungnh.security.constants.SecurityConstant.LOGOUT_ENDPOINT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(
        basePackages = "uet.hungnh.security",
        excludeFilters = @ComponentScan.Filter({Configuration.class})
)
@PropertySource("classpath:/security.properties")
public class JwtSecurityConfig extends AbstractSecurityConfig {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(jwtAuthenticationProvider())
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
                .logoutSuccessHandler(logoutSuccessHandler());

        http.addFilterBefore(new UsernamePasswordAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
        http.addFilterAfter(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationProvider jwtAuthenticationProvider() throws JOSEException {
        return new JwtAuthenticationProvider(jwsVerifier());
    }

    @Bean
    public JWSSigner jwsSigner() throws KeyLengthException {
        return new MACSigner(jwtSecretKey);
    }

    @Bean
    public JWSVerifier jwsVerifier() throws JOSEException {
        return new MACVerifier(jwtSecretKey);
    }
}
