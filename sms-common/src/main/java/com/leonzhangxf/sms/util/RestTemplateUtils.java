package com.leonzhangxf.sms.util;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

/**
 * 调整过的Spring的RestTemplate对象
 * 可能不适用与所有场景，请针对具体场景进行改动，或不使用该工具类。
 *
 * @author leonzhangxf 20180907
 */
public class RestTemplateUtils {

    private static Logger logger = LoggerFactory.getLogger(RestTemplateUtils.class);

    private static RestTemplate restTemplate;

    /**
     * 通过此方法获取一个优化过的RestTemplate实例
     */
    public static RestTemplate getRestTemplate() {
        if (null != restTemplate) {
            return restTemplate;
        }

        //跳过HTTPS检查
        SSLContext sslContext;
        try {
            sslContext = SSLContexts.custom()
                .loadTrustMaterial((X509Certificate[] chain, String authType) -> true).build();

            //两个方案的原因：HttpClients构造的方式不同
            //Plan 1
//            CloseableHttpClient httpClient = HttpClients.custom()
//                    .setSSLContext(sslContext)
//                    .setSSLHostnameVerifier((String host, SSLSession sslSession) -> true)
//                    .build();

            //Plan 2
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext,
                (String host, SSLSession sslSession) -> true);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            restTemplate = new RestTemplate(requestFactory);
        } catch (Exception ex) {
            logger.error("####SSL 封装出现异常", ex);
        }

        reInitMessageConverter(restTemplate);
        return restTemplate;
    }

    /**
     * 初始化RestTemplate，RestTemplate会默认添加HttpMessageConverter
     * <p>
     * 添加的StringHttpMessageConverter非UTF-8
     * 所以先要移除原有的StringHttpMessageConverter，
     * 再添加一个字符集为UTF-8的StringHttpMessageConvert
     */
    private static void reInitMessageConverter(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }

        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8,
            MediaType.APPLICATION_JSON));

        converterList.add(jsonConverter);
        converterList.add(converter);
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = RestTemplateUtils.getRestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://www.baidu.com",
            HttpEntity.EMPTY, String.class);

        System.out.println(responseEntity.getStatusCodeValue());
        System.out.println(responseEntity.getBody());
    }

}
