package com.leonzhangxf.sms;

import com.leonzhangxf.sms.configuration.CacheConfiguration;
import com.leonzhangxf.sms.configuration.CorsConfiguration;
import com.leonzhangxf.sms.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * SMS 内部API服务
 */
@SpringBootApplication
@EnableTransactionManagement
@Import({
        SwaggerConfiguration.class,
        CorsConfiguration.class,
        CacheConfiguration.class,
        RootExceptionHandler.class
})
@Controller
public class SMSAPIDistApplication {

    public static void main(String[] args) {
        SpringApplication.run(SMSAPIDistApplication.class, args);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }
}
