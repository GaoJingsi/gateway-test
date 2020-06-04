package com.gaojingsi.gatewaytest.predicates;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Component
public class SwitchRoutePredicateFactory extends AbstractRoutePredicateFactory<SwitchConfig> {
    public SwitchRoutePredicateFactory() {
        super(SwitchConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(SwitchConfig config) {
        return serverWebExchange -> config.isServiceSwitch();
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("serviceSwitch");
    }
}
