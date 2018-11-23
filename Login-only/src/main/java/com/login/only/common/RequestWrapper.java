package com.login.only.common;

import com.login.only.common.Filter.LoginFilter;
import com.login.only.util.MyRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class RequestWrapper extends HttpServletRequestWrapper {

    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private final byte[] body;

    public RequestWrapper(HttpServletRequest request) throws Exception {
        super(request);
//        body = MyRequestUtil.getBody(request).getBytes(Charset.forName("UTF-8"));
        body=MyRequestUtil.getByteByStream(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }



}