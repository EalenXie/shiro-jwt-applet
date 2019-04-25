package name.ealen.infrastructure.exception.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by EalenXie on 2019/4/16 15:53.
 */
public class ExceptionResponse implements Serializable {

    private static final ThreadLocal<ExceptionResponse> EXCEPTION_RESPONSE = new ThreadLocal<>();

    private Integer id;

    private String requestUrl;

    private String requestMethod;

    private String requestBody;

    private String requesterIp;

    private String userAgent;

    private String responseBody;

    private Integer statusCode;

    private String statusText;

    private String statusReasonPhrase;

    private String throwable;

    private Date throwableTime;

    private String message;

    private String localizedMessage;

    private transient Object errorParams;

    public static synchronized ExceptionResponse getCurrentException() {
        ExceptionResponse response = EXCEPTION_RESPONSE.get();
        if (response == null) {
            response = new ExceptionResponse();
            EXCEPTION_RESPONSE.set(response);
        }
        return response;
    }

    public static void removeExceptionResponse() {
        EXCEPTION_RESPONSE.remove();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequesterIp() {
        return requesterIp;
    }

    public void setRequesterIp(String requesterIp) {
        this.requesterIp = requesterIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusReasonPhrase() {
        return statusReasonPhrase;
    }

    public void setStatusReasonPhrase(String statusReasonPhrase) {
        this.statusReasonPhrase = statusReasonPhrase;
    }

    public String getThrowable() {
        return throwable;
    }

    public void setThrowable(String throwable) {
        this.throwable = throwable;
    }

    public Date getThrowableTime() {
        return throwableTime;
    }

    public void setThrowableTime(Date throwableTime) {
        this.throwableTime = throwableTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    public Object getErrorParams() {
        return errorParams;
    }

    public void setErrorParams(Object errorParams) {
        this.errorParams = errorParams;
    }
}
