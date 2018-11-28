package name.ealen.domain.repository;

import name.ealen.domain.entity.WxAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by EalenXie on 2018/11/26 10:32.
 */
public interface WxAccountRepository extends JpaRepository<WxAccount, Integer> {

    /**
     * 根据OpenId查询用户信息
     */
    WxAccount findByWxOpenid(String wxOpenId);
}
