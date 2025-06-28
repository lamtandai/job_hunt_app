package application.project.util.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import application.project.domain.Exception.EmailExistException;
import application.project.domain.Exception.InvalidException;
import application.project.domain.dto.response.RestResponse;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = {
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            EmailExistException.class,
            InvalidException.class
    })
    public ResponseEntity<RestResponse<Object>> handleException(Exception ex) {
        RestResponse<Object> res = RestResponse
                .builder()
                .setStatusCode(HttpStatus.BAD_REQUEST.value())
                .setError(ex.getMessage())
                .setMessage(ex.getMessage() + " Retry again!")
                .build();
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(value = {
            NoResourceFoundException.class
    })
    public ResponseEntity<RestResponse<Object>> handleNotFoundException(Exception ex) {
        RestResponse<Object> res = RestResponse
                .builder()
                .setStatusCode(HttpStatus.NOT_FOUND.value())
                .setError(ex.getMessage())
                .setMessage(ex.getMessage() + " Retry again!")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validateException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        Map<String, String> errors = fieldErrors
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        RestResponse<Object> res = RestResponse
                .builder()
                .setStatusCode(HttpStatus.BAD_REQUEST.value())
                .setError(exception.getBody().getDetail())
                .setMessage(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
    // @ExceptionHandler(NumberFormatException.class)
    // public ResponseEntity<RestResponse<Object>>
    // numberFormatException(NumberFormatException exception){
    // RestResponse<Object> res = RestResponse
    // .builder()
    // .setStatusCode(HttpStatus.BAD_REQUEST.value())
    // .setError(exception.getLocalizedMessage())
    // .setMessage("Page not found! Please try again!")
    // .build();

    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    // }
}
