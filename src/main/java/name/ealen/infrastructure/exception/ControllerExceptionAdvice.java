package name.ealen.infrastructure.exception;

import com.alibaba.fastjson.JSON;
import name.ealen.infrastructure.exception.model.ExceptionResponse;
import name.ealen.infrastructure.util.HttpUtil;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by EalenXie on 2018/11/8 16:25.
 * 全局异常 及其自定义异常 返回处理
 */
@ControllerAdvice
public class ControllerExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionAdvice.class);

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity throwable(Throwable throwable, HttpServletRequest request) {
        ExceptionResponse response = ExceptionResponse.getCurrentException();
        try {
            response.setThrowable("" + throwable);
            response.setThrowableTime(new Date());
            response.setLocalizedMessage(throwable.getLocalizedMessage());
            response.setMessage(throwable.getMessage());
            log.error("Exception printStackTrace", throwable);
            if (Objects.nonNull(request)) {
                response.setRequesterIp(HttpUtil.getIpAddress(request));
                response.setRequestUrl(request.getRequestURL().toString());
                response.setRequestMethod(request.getMethod());
                response.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
                Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
                if (Objects.nonNull(statusCode)) response.setStatusCode(statusCode);
                else response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            if (throwable instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException exception = ((MethodArgumentNotValidException) throwable);
                if (!Objects.isNull(exception.getBindingResult().getTarget())) {
                    response.setRequestBody(JSON.toJSON(exception.getBindingResult().getTarget()).toString());
                }
                List<FieldError> fieldErrors = ((MethodArgumentNotValidException) throwable).getBindingResult().getFieldErrors();
                Map<String, String> params = new HashMap<>();
                response.setErrorParams(JSON.toJSON(params));
                for (FieldError error : fieldErrors) {
                    params.put(error.getField(), error.getDefaultMessage());
                }
                response.setErrorParams(JSON.toJSON(params));
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            } else if (throwable instanceof HttpServerErrorException) {
                HttpServerErrorException serverError = (HttpServerErrorException) throwable;
                HttpStatus status = serverError.getStatusCode();
                response.setResponseBody("" + serverError.getResponseBodyAsString());
                response.setStatusCode(status.value());
                response.setStatusText("" + serverError.getStatusText());
                response.setStatusReasonPhrase(status.getReasonPhrase());
            } else if (throwable instanceof HttpClientErrorException) {
                HttpClientErrorException clientError = (HttpClientErrorException) throwable;
                HttpStatus status = clientError.getStatusCode();
                response.setResponseBody("" + clientError.getResponseBodyAsString());
                response.setStatusCode(status.value());
                response.setStatusText("" + clientError.getStatusText());
                response.setStatusReasonPhrase(status.getReasonPhrase());
            } else if (throwable instanceof SQLException) {
                response.setStatusText(((SQLException) throwable).getSQLState());
            } else if (throwable instanceof UnauthenticatedException) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        } catch (Exception ignore) {
            log.info("Exception ignore");
        } finally {
            ExceptionResponse.removeExceptionResponse();
        }
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}