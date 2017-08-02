package com.qatang.team.daemon.config.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qatang.team.core.bean.ErrorInfo;
import com.qatang.team.core.exception.ClientExceptionConverter;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author wangzhiliang
 */
public class SpringWebClientErrorDecoder implements ErrorDecoder {
    private ErrorDecoder delegate = new Default();

    private static final Logger logger = LoggerFactory.getLogger(SpringWebClientErrorDecoder.class);

    private ClientExceptionConverter clientExceptionConverter = ClientExceptionConverter.instance();

    @Autowired
    private ObjectMapper customObjectMapper;

    @Override
    public Exception decode(String s, Response response) {
        HttpHeaders responseHeaders = new HttpHeaders();
        response.headers().forEach((key, value) -> responseHeaders.put(key, new ArrayList<>(value)));

        HttpStatus statusCode = HttpStatus.valueOf(response.status());
        String statusText = response.reason();

        String responseBody = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(response.body().asInputStream()));
            String line;
            StringBuilder buf = new StringBuilder();
            while ( (line = br.readLine()) != null ) {
                buf.append(line);
            }

            responseBody = buf.toString();
        } catch (Exception e) {
            logger.error("读取响应数据异常", e.getMessage());
        }

        logger.error("响应信息：{}", responseBody);

        if (response.status() >= 400 && response.status() <= 499) {
            ErrorInfo errorInfo = null;
            try {
                errorInfo = customObjectMapper.readValue(responseBody, ErrorInfo.class);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            return clientExceptionConverter.converter(errorInfo);
        }

        if (response.status() >= 500 && response.status() <= 599) {
            return new HttpServerErrorException(statusCode, statusText, responseHeaders, responseBody.getBytes(), null);
        }
        return delegate.decode(s, response);
    }
}
