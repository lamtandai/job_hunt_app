package application.project.util;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import application.project.domain.dto.response.RestResponse;
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

        if (body instanceof RestResponse || body instanceof String || body instanceof Resource) {
            return body;
        }

        

        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();
        // case error:
        RestResponse<Object> res ;

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
