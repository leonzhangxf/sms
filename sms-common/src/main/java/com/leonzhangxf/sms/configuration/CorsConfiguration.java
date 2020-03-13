package com.leonzhangxf.sms.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The server cross origin support configuration.
 *
 * @author leonzhangxf 20180808
 */
@ConfigurationProperties("spring.cors")
public class CorsConfiguration implements WebMvcConfigurer {

    /**
     * The origin allowed to send request to this server.
     * Response with the header: Access-Control-Allow-Origin.
     * Default all origins are allowed.
     */
    private List<String> allowOriginList = new ArrayList<>(Collections.singletonList("*"));

    /**
     * The headers allowed adding to origin request header.
     * Response with the header: Access-Control-Allow-Headers.
     * Default all headers allowed.
     */
    private List<String> allowedOriginHeaders = new ArrayList<>(Collections.singletonList("*"));

    /**
     * The http method list allowed in origin request methods.
     * Response with the header: Access-Control-Allow-Methods.
     * Default allowed GET, POST, PUT, DELETE, OPTIONS
     */
    private List<String> allowedOriginMethods = new ArrayList<>(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    /**
     * If allow the request with credentials.
     * Response with the header: Access-Control-Allow-Credentials.
     * Default true.
     */
    private boolean allowCredentials = true;

    /**
     * Configure how long in seconds the response from a pre-flight request
     * can be cached by clients.
     * Response with the header: Access-Control-Max-Age.
     * <p>By default this is set to 1800 seconds (30 minutes).
     */
    private long maxAge = 1800;

    /**
     * 添加跨域配置
     * <p>
     * Configure cross origin requests processing.
     *
     * @since 4.2
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration corsRegistration = registry.addMapping("/**");
        corsRegistration.allowedOrigins(allowOriginList.toArray(new String[]{}))
                .allowedHeaders(allowedOriginHeaders.toArray(new String[]{}))
                .allowedMethods(allowedOriginMethods.toArray(new String[]{}))
                .allowCredentials(allowCredentials)
                .maxAge(maxAge);
    }

    public List<String> getAllowOriginList() {
        return allowOriginList;
    }

    public void setAllowOriginList(List<String> allowOriginList) {
        this.allowOriginList = allowOriginList;
    }

    public List<String> getAllowedOriginHeaders() {
        return allowedOriginHeaders;
    }

    public void setAllowedOriginHeaders(List<String> allowedOriginHeaders) {
        this.allowedOriginHeaders = allowedOriginHeaders;
    }

    public List<String> getAllowedOriginMethods() {
        return allowedOriginMethods;
    }

    public void setAllowedOriginMethods(List<String> allowedOriginMethods) {
        this.allowedOriginMethods = allowedOriginMethods;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }
}
