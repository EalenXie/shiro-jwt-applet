package name.ealen.domain.vo;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by EalenXie on 2018/11/22 18:21.
 * 鉴权用的token vo ，实现 AuthenticationToken
 */
@Data
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
