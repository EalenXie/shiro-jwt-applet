package name.ealen.domain.vo;


import lombok.Data;

/**
 * 微信小程序 Code2Session 接口返回值 对象
 * 具体可以参考小程序官方API说明 : https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/code2Session.html
 */
@Data
public class Code2SessionResponse {
    private String openid;
    private String session_key;
    private String unionid;
    private String errcode = "0";
    private String errmsg;
    private int expires_in;
}