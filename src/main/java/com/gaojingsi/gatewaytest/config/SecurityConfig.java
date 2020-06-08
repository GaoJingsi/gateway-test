package com.gaojingsi.gatewaytest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.reactive.result.method.annotation.OAuth2AuthorizedClientArgumentResolver;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;
import reactor.core.publisher.Mono;

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

//    @Bean
//    public DefaultReactiveOAuth2AuthorizedClientManager authorizedClientManager(
//            ReactiveClientRegistrationRepository clientRegistrationRepository,
//            ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {
//
//        ReactiveOAuth2AuthorizedClientProvider auth2AuthorizedClientProvider =
//                ReactiveOAuth2AuthorizedClientProviderBuilder
//                        .builder()
//                        .clientCredentials()
//                        .build();
//
//        DefaultReactiveOAuth2AuthorizedClientManager authorizedClientManager =
//                new DefaultReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
//        authorizedClientManager.setAuthorizedClientProvider(auth2AuthorizedClientProvider);
//
//        return authorizedClientManager;
//    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http,
            ReactiveClientRegistrationRepository clientRegistrationRepository) {
        http
                // ...
                .authorizeExchange(exchange -> {
                    exchange.anyExchange().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .clientRegistrationRepository(clientRegistrationRepository);
                })
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

//    @Bean
//    public ServerOAuth2AuthorizedClientExchangeFilterFunction serverOAuth2AuthorizedClientExchangeFilterFunction(DefaultReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
//        return new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
//    }

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
