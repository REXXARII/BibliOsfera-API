package cl.duoc.bibliosfera.bibliosfera_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // <-- ESTA ES LA LÍNEA CLAVE DEL PASO 38
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // --- Endpoints Públicos ---
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/doc/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                // --- Endpoints para Clientes, Empleados y Admins ---
                .requestMatchers(HttpMethod.GET, "/api/v1/obras/**", "/api/v1/autores/**", "/api/v1/categorias/**", "/api/v1/editoriales/**").authenticated()

                // --- Endpoints para Empleados y Admins ---
                .requestMatchers(HttpMethod.POST, "/api/v1/obras", "/api/v1/autores", "/api/v1/categorias", "/api/v1/editoriales").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/ediciones", "/api/v1/ejemplares").hasAnyRole("EMPLEADO", "ADMIN")
                
                // Nota: El endpoint de préstamos ya está protegido con @PreAuthorize en el controlador.

                // --- Regla Final ---
                .anyRequest().authenticated()
            )
            .formLogin(withDefaults())
            .logout(logout -> logout.permitAll());
        
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}
