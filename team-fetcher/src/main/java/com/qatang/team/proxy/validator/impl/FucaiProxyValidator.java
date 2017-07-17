package com.qatang.team.proxy.validator.impl;

import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.proxy.exception.ProxyException;
import com.qatang.team.proxy.validator.AbstractProxyValidator;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author qatang
 */
public class FucaiProxyValidator extends AbstractProxyValidator {
    private final static ProxyValidatorType PROXY_VALIDATOR_TYPE = ProxyValidatorType.V_FUCAI;
    private final static String TARGET_URL = "http://www.cwl.gov.cn";
    private final static String TARGET_ENCODING = "UTF-8";

    @Override
    protected ProxyValidatorType getProxyValidatorType() {
        return PROXY_VALIDATOR_TYPE;
    }

    @Override
    protected String getTargetUrl() {
        return TARGET_URL;
    }

    @Override
    protected String getTargetEncoding() {
        return TARGET_ENCODING;
    }

    @Override
    protected void validateContent(String html) throws ProxyException {
        String check = "中国福利彩票官方网站";
        Document document = Jsoup.parse(html);
        String title = document.select("head title").text();
        if (!StringUtils.contains(title, check)) {
            throw new ProxyException(String.format("代理检测失败：内容不符，proxyValidatorType=%s, source=%s， check=%s", PROXY_VALIDATOR_TYPE.getName(), title, check));
        }
    }
}
