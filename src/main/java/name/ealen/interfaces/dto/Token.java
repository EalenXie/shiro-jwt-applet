package name.ealen.interfaces.dto;

/**
 * Created by EalenXie on 2018/11/26 18:49.
 * DTO 返回值token对象
 */
public class Token {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
