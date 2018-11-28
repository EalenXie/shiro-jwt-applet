package name.ealen.infrastructure.exception;

import com.alibaba.fastjson.JSON;
import name.ealen.infrastructure.util.HttpUtil;
import name.ealen.infrastructure.util.TimeUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by EalenXie on 2018/11/8 16:25.
 * 全局异常 及其自定义异常 返回处理
 */
@ControllerAdvice
public class ControllerExceptionAdvice {

    private final Logger log = LoggerFactory.getLogger(ControllerExceptionAdvice.class);


    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity Throwable(Throwable throwable, HttpServletRequest request) {
        Map<String, String> resultMap = getThrowable(throwable);
        if (request != null) {
            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            resultMap.put("Requester-Ip", HttpUtil.getIpAddress(request));
            resultMap.put("Requester-Agent", request.getHeader("user-agent"));
            if (statusCode != null) {
                new ResponseEntity<>(JSON.toJSON(resultMap).toString(), HttpStatus.valueOf(statusCode));
            }
        }
        return new ResponseEntity<>(JSON.toJSON(resultMap).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity AuthenticationException(AuthenticationException serverError) {
        Map<String, String> resultMap = getThrowable(serverError);
        return new ResponseEntity<>(JSON.toJSON(resultMap).toString(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(value = HttpServerErrorException.class)
    public ResponseEntity HttpServerErrorException(HttpServerErrorException serverError) {
        Map<String, String> resultMap = getThrowable(serverError);
        HttpStatus status = serverError.getStatusCode();
        resultMap.put("responseBody", "" + serverError.getResponseBodyAsString());
        resultMap.put("statusCode", "" + status.toString());
        resultMap.put("statusText", "" + serverError.getStatusText());
        resultMap.put("statusReasonPhrase", "" + status.getReasonPhrase());
        return new ResponseEntity<>(JSON.toJSON(resultMap).toString(), status);
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity HttpClientErrorException(HttpClientErrorException clientError) {
        Map<String, String> resultMap = getThrowable(clientError);
        HttpStatus status = clientError.getStatusCode();
        resultMap.put("responseBody", "" + clientError.getResponseBodyAsString());
        resultMap.put("statusCode", "" + clientError.getStatusCode().toString());
        resultMap.put("statusText", "" + clientError.getStatusText());
        resultMap.put("statusReasonPhrase", "" + status.getReasonPhrase());
        return new ResponseEntity<>(JSON.toJSON(resultMap).toString(), status);
    }


    /**
     * 公共异常信息
     */
    private Map<String, String> getThrowable(Throwable throwable) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("throwable", "" + throwable);
        resultMap.put("throwableTime", "" + TimeUtil.getCurrentDateTime());
        resultMap.put("message", "" + throwable.getMessage());
        resultMap.put("localizedMessage", "" + throwable.getLocalizedMessage());
        log.error("Exception : {}", JSON.toJSON(resultMap));
        throwable.printStackTrace();
        return resultMap;
    }

}