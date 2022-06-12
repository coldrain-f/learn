package me.coldrain.learn.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigurationTest {

    @Test
    void pathRequest_테스트() {
        /**
         * 디버깅 해서 테스트
         */
        StaticResourceRequest.StaticResourceRequestMatcher commonLocations
                = PathRequest.toStaticResources().atCommonLocations();
        System.out.println("commonLocations = " + commonLocations);
    }

}