package com.leonzhangxf.sms.exception;

/**
 * 违法的用户信息
 * <p>
 * 在获取Keycloak 当前用户信息时可能抛出此异常信息
 *
 * @author leonzhangxf 20180911
 */
public class IllegalPrincipalException extends RuntimeException {

    private static final long serialVersionUID = 718692082728964931L;

    public IllegalPrincipalException(String message) {
        super(message);
    }
}
