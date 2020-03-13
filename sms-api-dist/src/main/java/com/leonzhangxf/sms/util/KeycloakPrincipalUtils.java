package com.leonzhangxf.sms.util;

import com.leonzhangxf.sms.exception.IllegalPrincipalException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;

/**
 * Keycloak 用户工具类
 *
 * @author leonzhangxf 20180911
 */
public class KeycloakPrincipalUtils {

    /**
     * 获取Keycloak 全局对象
     *
     * @return Keycloak 全局对象
     */
    public static KeycloakSecurityContext getKeycloakSecurityContext() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            throw new IllegalPrincipalException("未解析到用户信息！");
        }
        Principal principal = attributes.getRequest().getUserPrincipal();
        if (principal == null) {
            throw new IllegalPrincipalException("未解析到用户信息！");
        }
        if (!(principal instanceof KeycloakPrincipal)) {
            throw new IllegalStateException("解析到的用户信息不是统一认证中心用户信息！");
        }

        return ((KeycloakPrincipal) principal).getKeycloakSecurityContext();
    }

    /**
     * 获取当前登录用户名
     */
    public static String getUsername() {
        return getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    /**
     * 获取当前登录用户名称
     */
    public static String getName() {
        return getKeycloakSecurityContext().getToken().getName();
    }
}
