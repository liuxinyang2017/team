package com.qatang.team.proxy.validator;

import com.google.common.base.MoreObjects;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
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
public abstract class AbstractProxyValidator implements IProxyValidator {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取代理验证器类型
     * @return 代理验证器类型
     */
    protected abstract ProxyValidatorType getProxyValidatorType();

    /**
     * 获取检测目标地址
     * @return 检测目标地址
     */
    protected abstract String getTargetUrl();

    /**
     * 获取检测目标编码
     * @return 检测目标编码
     */
    protected abstract String getTargetEncoding();

    /**
     * 检查网页内容
     * @param html 网页内容
     */
    protected abstract void validateContent(String html) throws ProxyException;

    @Override
    public void validate(ProxyInfo proxyInfo) throws ProxyException {
        logger.info(String.format("开始验证代理可用性：proxyValidatorType=%s，测试url: %s, proxy=%s:%s@%s", this.getProxyValidatorType().getName(), this.getTargetUrl(), proxyInfo.getHost(), proxyInfo.getPort(), proxyInfo.getProxyType()));
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
                .url(this.getTargetUrl())
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            try (ResponseBody responseBody = response.body()) {
                if (responseBody == null || response.code() != 200) {
                    throw new ProxyException(String.format("代理测试失败，返回code=%s, 测试url: %s, proxy=%s:%s@%s", response.code(), this.getTargetUrl(), proxyInfo.getHost(), proxyInfo.getPort(), proxyInfo.getProxyType()));
                }
                String html = null;
                // 编码检测
                if (StringUtils.isNotBlank(this.getTargetEncoding())) {
                    if ("GB".equalsIgnoreCase(StringUtils.substring(this.getTargetEncoding(), 0, 2)) || "UTF-8".equalsIgnoreCase(this.getTargetEncoding())) {
                        byte[] bytes = responseBody.bytes();

                        // 对常用编码抓取结果进行检测以剔除无效代理
                        CharsetDetector detector = new CharsetDetector();
                        detector.setText(bytes);
                        html = new String(bytes);

                        CharsetMatch match = detector.detect();
                        String detectedEncoding = match.getName();
                        if (!("GB".equalsIgnoreCase(StringUtils.substring(detectedEncoding, 0, 2)) && "GB".equalsIgnoreCase(StringUtils.substring(this.getTargetEncoding(), 0, 2))
                                || "UTF-8".equalsIgnoreCase(detectedEncoding) && "UTF-8".equalsIgnoreCase(this.getTargetEncoding())
                                || "ISO-8859-1".equals(detectedEncoding))) {
                            throw new ProxyException(String.format("代理测试失败，返回encoding=%s，与targetEncoding=%s不一致, 测试url: %s, proxy=%s:%s@%s", response.code(), this.getTargetEncoding(), this.getTargetUrl(), proxyInfo.getHost(), proxyInfo.getPort(), proxyInfo.getProxyType()));
                        }
                    }
                }

                // 内容检测
                if (html == null) {
                    html = responseBody.string();
                }
                this.validateContent(html);
                logger.info(String.format("结束验证代理可用性：proxyValidatorType=%s，测试url: %s, proxy=%s:%s@%s", this.getProxyValidatorType().getName(), this.getTargetUrl(), proxyInfo.getHost(), proxyInfo.getPort(), proxyInfo.getProxyType()));
            }
        } catch (Exception e) {
            String msg = String.format("代理测试失败, 错误信息：%s，测试url: %s, proxy=%s:%s@%s", e.getMessage(), this.getTargetUrl(), proxyInfo.getHost(), proxyInfo.getPort(), proxyInfo.getProxyType());
            logger.error(msg);
//            logger.error(e.getMessage(), e);
            throw new ProxyException(msg);
        }
    }
}
