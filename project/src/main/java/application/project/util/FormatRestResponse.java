package application.project.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import application.project.domain.RestResponse.RestResponse;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        if (body instanceof RestResponse) {
            return body;
        }

        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();
        // case error:
        RestResponse<Object> res = RestResponse.builder().build();

        if (status >= 400) {
            res = RestResponse
                    .builder()
                    .setError(body.toString())
                    .setMessage("Api get failed!")
                    .setStatusCode(status)
                    .build();
        } else {
            res = RestResponse
                    .builder()
                    .setData(body)
                    .setMessage("APi is called successfully!")
                    .setStatusCode(status)
                    .build();
        }

        return res;
    }

}
