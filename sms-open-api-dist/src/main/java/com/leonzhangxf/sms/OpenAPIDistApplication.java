package com.leonzhangxf.sms;

import com.leonzhangxf.sms.configuration.CacheConfiguration;
import com.leonzhangxf.sms.configuration.CorsConfiguration;
import com.leonzhangxf.sms.configuration.SwaggerConfiguration;
import com.leonzhangxf.sms.constant.SmsAuthConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Import({
        CorsConfiguration.class,
        SwaggerConfiguration.class,
        CacheConfiguration.class,
        SmsAuthConstant.class,
})
@EnableAsync
public class OpenAPIDistApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAPIDistApplication.class, args);
    }
}
