package mikhail.task.configs;

import lombok.RequiredArgsConstructor;
import mikhail.task.security.HarvestResultAccessManager;
import mikhail.task.security.JwtFilter;
import mikhail.task.security.WorkerAccessManager;
import mikhail.task.services.WorkerDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final WorkerDetailsService workerDetailsService;
    private final JwtFilter jwtFilter;
    private final WorkerAccessManager workerAccessManager;
    private final HarvestResultAccessManager harvestResultAccessManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/v1/auth").permitAll()
                                .requestMatchers(HttpMethod.GET, "/v1/products").hasAnyRole("WORKER", "ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.POST, "/v1/harvests").hasAnyRole("WORKER", "ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.PUT, "/v1/harvests/{id}").access(harvestResultAccessManager)
                                .requestMatchers(HttpMethod.DELETE, "/v1/harvests/{id}").access(harvestResultAccessManager)
                                .requestMatchers(HttpMethod.GET, "/v1/users/{id}/harvests").access(workerAccessManager)
                                .requestMatchers("/v1/products", "/v1/products/*", "/v1/users", "/v1/users/*", "/v1/harvests/{id}").hasAnyRole("ADMIN", "OWNER")
                                .anyRequest().hasRole("OWNER")
                )
                .exceptionHandling(exc ->
                        exc.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(workerDetailsService)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
