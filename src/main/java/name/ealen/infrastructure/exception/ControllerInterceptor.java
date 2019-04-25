package name.ealen.infrastructure.exception;

import com.alibaba.fastjson.JSON;
import name.ealen.infrastructure.exception.model.ExceptionResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by EalenXie on 2018/9/7 14:19.
 * AOP打印日志 : 请求的对象,请求参数,返回数据,请求状态,内部方法耗时
 */
@Aspect
@Component
public class ControllerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ControllerInterceptor.class);
    private final String profiles;
    private final String appName;

    public ControllerInterceptor(Environment environment) {
        String[] defaultProfiles = environment.getDefaultProfiles();
        String[] activeProfiles = environment.getActiveProfiles();
        StringBuilder profile = new StringBuilder(defaultProfiles[0]);
        if (defaultProfiles.length > 1) {
            for (int i = 1; i < defaultProfiles.length; i++) {
                profile.append(",").append(defaultProfiles[i]);
            }
        }
        if (activeProfiles.length != 0) {
            profile = new StringBuilder(activeProfiles[0]);
            if (activeProfiles.length > 1) {
                for (int i = 1; i < activeProfiles.length; i++) {
                    profile.append(",").append(activeProfiles[i]);
                }
            }
        }
        this.profiles = profile.toString();
        String applicationName;
        try {
            applicationName = environment.getProperty("spring.application.name");
        } catch (Exception ignore) {
            applicationName = "unnamed";
        }
        this.appName = applicationName;
    }

    /**
     * 注意 : pjp.proceed()执行的异常请务必抛出，交由ControllerAdvice捕捉到并处理
     */
    @Around(value = "execution (*  name.ealen.interfaces.facade.*.*(..))")
    public Object processApiFacade(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String name = pjp.getTarget().getClass().getSimpleName();
        String method = pjp.getSignature().getName();
        Object result;
        try {
            Object[] requestParams = pjp.getArgs();
            log.info(String.format("RequestTarget : %s start with %s %s.%s", appName, profiles, name, method));
            if (requestParams.length > 0) {     //日志打印请求参数
                try {
                    log.info("RequestParam : {}", JSON.toJSON(requestParams));
                    ExceptionResponse.getCurrentException().setRequestBody(JSON.toJSON(requestParams).toString());
                } catch (Exception e) {
                    for (Object param : requestParams) {
                        try {
                            log.info("RequestParam : {}", JSON.toJSON(param));
                        } catch (Exception ig) {
                            log.info("RequestParam : {}", param);
                        }
                    }
                }
            }
            result = pjp.proceed();
            try {
                ExceptionResponse.getCurrentException().setResponseBody(JSON.toJSONString(result));
                log.info("ResponseBody: {}", JSON.toJSONString(result));
            } catch (Exception ignore) {
                log.info("ResponseBody Exception ignore");
            } finally {
                ExceptionResponse.removeExceptionResponse();
            }
        } finally {
            log.info("Internal Method Cost Time: {}ms", System.currentTimeMillis() - startTime);
        }
        return result;
    }

}
