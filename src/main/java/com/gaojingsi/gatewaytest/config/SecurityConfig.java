package com.gaojingsi.gatewaytest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//        return new MapReactiveUserDetailsService(user);
//    }

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository) {
        http
                // ...
                .authorizeExchange(exchange -> {
                    exchange.anyExchange().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .clientRegistrationRepository(clientRegistrationRepository);
                })
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

//    @Bean
//    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrations) {
//        http
//                // ...
//                .authorizeExchange(exchange -> {
//                    exchange.anyExchange().authenticated();
//                })
//                .oauth2Login(oauth2 -> oauth2
//                        .clientRegistrationRepository(clientRegistrations)
//                )
//                .csrf(ServerHttpSecurity.CsrfSpec::disable);
//        return http.build();
//    }

//    @Bean
//    ReactiveClientRegistrationRepository clientRegistrations() {
//        ClientRegistration clientRegistration = ClientRegistrations
//                .fromIssuerLocation("http://192.168.0.254:8000/.well-known/oauth-authorization-server/issuer")
//                .clientId("yibaoAppSecret")
//                .clientSecret("qaManagerSecret")
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .scope("all")
//                .tokenUri("http://192.168.0.254:8000/oauth/token")
//                .build();
//        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
//    }

    @Bean
    public WebSessionIdResolver webSessionIdResolver() {
        CookieWebSessionIdResolver resolver = new CookieWebSessionIdResolver();
        // 重写定义 cookie 名字
        resolver.setCookieName("GATEWAYSESSIONID");
        resolver.addCookieInitializer((builder) -> builder.path("/"));
        resolver.addCookieInitializer((builder) -> builder.sameSite("Strict"));
        return resolver;
    }

//    @Bean
//    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
//        return new HiddenHttpMethodFilter() {
//            @Override
//            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//                return chain.filter(exchange);
//            }
//        };
//    }

}
