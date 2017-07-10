package com.qatang.team.proxy.validator;

import com.google.common.base.MoreObjects;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.exception.ProxyException;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author qatang
 */
public class ProxyValidator {
    private final static Logger logger = LoggerFactory.getLogger(ProxyValidator.class);

    /**
     * 返回代理响应时间，毫秒
     * @param proxyInfo 代理信息
     * @param targetUrl 测试地址
     * @param targetEncoding 测试网页编码
     * @return 代理响应时间，毫秒
     * @throws ProxyException 异常
     */
    public static long validate(ProxyInfo proxyInfo, String targetUrl, String targetEncoding) throws ProxyException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyInfo.getHost(), proxyInfo.getPort()));

        Authenticator proxyAuthenticator = null;

        if (StringUtils.isNotBlank(proxyInfo.getUsername())) {
            proxyAuthenticator = (route, response) -> {
                String credential = Credentials.basic(proxyInfo.getUsername(), MoreObjects.firstNonNull(proxyInfo.getPassword(), StringUtils.EMPTY));
                return response.request().newBuilder()
                        .header("Proxy-Authorization", credential)
                        .build();
            };
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (proxyAuthenticator != null) {
            builder.proxyAuthenticator(proxyAuthenticator);
        }
        OkHttpClient httpClient = builder.proxy(proxy)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(targetUrl)
                .build();

        long begin = System.currentTimeMillis();
        try (Response response = httpClient.newCall(request).execute()) {
            try (ResponseBody responseBody = response.body()) {
                if (responseBody == null || response.code() != 200) {
                    throw new ProxyException(String.format("代理测试失败，返回code=%s, 测试url: %s, proxy=%s:%s@%s", response.code(), targetUrl, proxyInfo.getHost(), proxyInfo.getPort(), proxyInfo.getProxyType()));
                }
                // 编码检测
                if (StringUtils.isNotBlank(targetEncoding)) {
                    if ("GB".equalsIgnoreCase(StringUtils.substring(targetEncoding, 0, 2)) || "UTF-8".equalsIgnoreCase(targetEncoding)) {
                        // 对常用编码抓取结果进行检测以剔除无效代理
                        CharsetDetector detector = new CharsetDetector();
                        detector.setText(responseBody.bytes());

                        CharsetMatch match = detector.detect();
                        String detectedEncoding = match.getName();
                        if (!("GB".equalsIgnoreCase(StringUtils.substring(detectedEncoding, 0, 2)) && "GB".equalsIgnoreCase(StringUtils.substring(targetEncoding, 0, 2))
                                || "UTF-8".equalsIgnoreCase(detectedEncoding) && "UTF-8".equalsIgnoreCase(targetEncoding)
                                || "ISO-8859-1".equals(detectedEncoding))) {
                            throw new ProxyException(String.format("代理测试失败，返回encoding=%s，与targetEncoding=%s不一致, 测试url: %s, proxy=%s:%s@%s", response.code(), targetEncoding, targetUrl, proxyInfo.getHost(), proxyInfo.getPort(), proxyInfo.getProxyType()));
                        }
                    }
                }
                long end = System.currentTimeMillis();
                return end - begin;
            }
        } catch (Exception e) {
            String msg = String.format("代理测试失败, 测试url: %s, proxy=%s:%s@%s", targetUrl, proxyInfo.getHost(), proxyInfo.getPort(), proxyInfo.getProxyType());
            logger.error(msg);
            logger.error(e.getMessage(), e);
            throw new ProxyException(msg);
        }
    }
}
