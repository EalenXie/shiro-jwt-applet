微信小程序登录完整实现例子(基于Shiro,JWT) 
===================

1. 微信小程序登陆流程图说明 : 
![avatar](https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/image/api-login.jpg)
 
    上图是小程序登陆的官方流程图说明,官方地址 : https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html
    
    如果有对官方说明不清楚的地方,也可参看我的博客 : https://www.cnblogs.com/ealenxie/p/9888064.html

2. 使用运行本例的前提注意以下几点 : 

    1. 本例是一个基于**Shiro**和**JWT**实现自定义登陆态的完整例子,你需要了解的技术栈 : Shiro,JWT,SpringBoot,JPA,Redis 

    2. 已有小程序的**appid**,**appsecret**,请准备一个数据源和Redis，并将这些信息配置到 src/main/resource/application.yml中

3. 代码结构说明,这是一个DDD结构的例子,其实也无须如此的,因为是在之前的一个代码结构的例子上写的,结构就没改: 
    
    1. interfaces/facade 下面有供我们小程序端调用的 api ，也就是我们常用的Controller层
    
    2. infrastructure 下面包含了所有重要组件的配置(Shiro的核心配置，JWT的过滤器和核心配置，全局异常通知，redis等配置信息)；包含常用工具类
    
    3. domain 下面包含了实体对象及其核心的业务处理逻辑，本例是微信用户登陆的处理逻辑
    
    4. application下面是交待本例要主要任务说明
    
    5. 如果对此结构有所兴趣，可以参考一下DDD相关资料，也可以参看我的博客 : https://www.cnblogs.com/ealenxie/p/9559781.html
    
    
4. 项目运行，检查数据库的连接是否正常，检查redis的连接是否正常。
    
5. 站在巨人的肩膀，本例参考内容 : 
    
    Shiro相关教程 : https://github.com/apache/shiro  http://shiro.apache.org/web.html
    
    Shiro+JWT简易教程 : https://github.com/Smith-Cruise/Spring-Boot-Shiro
    
6. 声明 :

    实现小程序的自定义登陆有很多种，本文是基于Shiro，JWT的一个实现。
    
    总结了Shiro部分使用和前辈们的JWT实现的相关经验还有自己的一些理解，如有雷同或不当之处，望各位见谅和帮忙指正。
    
    原创不易，感谢支持。
    
