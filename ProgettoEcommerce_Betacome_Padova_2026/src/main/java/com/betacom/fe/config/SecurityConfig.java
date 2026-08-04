package com.betacom.fe.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final JwtAuthenticationConverter jwtAuthenticationConverter;
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
        	.cors(Customizer.withDefaults())
        	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                	    "/images/**",
                		"/rest/User/login",
                        "/rest/User/create",
                        "/rest/User/forgotPassword",
                        "/rest/User/resetPassword",
                        "/rest/Prodotto/getAll",
                        "/rest/Prodotto/getById/**",
                        "/rest/Prodotto/search",
                        "/rest/Categoria/getAll",
                        "/rest/SottoCategoria/getAll",
                        "/rest/Upload/product",
                        "/rest/Upload/getUrl",
                        "/webhook/stripe"
                ).permitAll()
                
                .requestMatchers(
                    "/rest/Ruoli/**",
                    "/rest/StatoOrdine/**",
                    "/rest/StatoPagamento/**",
                    "/rest/Categoria/**",
                    "/rest/SottoCategoria/**"
                )
                .hasAuthority("Admin")

                .requestMatchers(
                	    "/rest/Prodotto/**",
                	    "/rest/DivisioneProdotto/**",
                	    "/rest/Sconto/**",
                	    "/rest/Upload/**"
                )
                .hasAnyAuthority("Admin", "Venditore")

                .requestMatchers(
                	"/rest/User/update",
                	"/rest/User/changePwd",
                	"/rest/User/changeUsername",
                	"/rest/Carrello/**",
                	"/rest/ProdottiCarrello/**",
                	"/rest/Indirizzi/**",
                	"/rest/Ordine/**",
                	"/rest/ProdottiOrdine/**",         
                	"/rest/Ricevuta/**",
                	"/rest/Notifica/**",
                	"/rest/Pagamenti/**"
                )
                .authenticated()
                .anyRequest().authenticated()
            ).oauth2ResourceServer(oauth ->
            oauth.jwt(jwt ->
            jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
            ));
        return http.build();
    }

    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}