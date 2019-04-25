package name.ealen.application;

import name.ealen.interfaces.dto.TokenDTO;

/**
 * Created by EalenXie on 2018/11/26 10:40.
 * 微信小程序自定义登陆 服务说明
 */
public interface WxAppletService {

    /**
     * 微信小程序用户登陆，完整流程可参考下面官方地址，本例中是按此流程开发
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html
     * 1 . 我们的微信小程序端传入code。
     * 2 . 调用微信code2session接口获取openid和session_key
     * 3 . 根据openid和session_key自定义登陆态(Token)
     * 4 . 返回自定义登陆态(Token)给小程序端。
     * 5 . 我们的小程序端调用其他需要认证的api，请在header的Authorization里面携带 token信息
     *
     * @param code 小程序端 调用 wx.login 获取到的code,用于调用 微信code2session接口
     * @return Token 返回后端 自定义登陆态 token  基于JWT实现
     */
    public TokenDTO wxUserLogin(String code);


}
