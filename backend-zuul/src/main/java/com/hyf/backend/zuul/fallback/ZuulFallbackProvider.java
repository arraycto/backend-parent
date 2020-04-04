package com.hyf.backend.zuul.fallback;

import com.alibaba.fastjson.JSON;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Elvis on 2020/3/1
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Component
public class ZuulFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.BAD_REQUEST.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                ResponseVO<String> responseVO = new ResponseVO<>();
                responseVO.setCode(-1);
                responseVO.setMsg("服务超时了,msg:" + cause.getMessage());
                responseVO.setData(null);
                return new ByteArrayInputStream(JSON.toJSONString(responseVO).getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return httpHeaders;
            }
        };
    }
}
