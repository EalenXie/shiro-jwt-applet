package name.ealen.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by EalenXie on 2018/11/26 10:26.
 * 实体 属性描述 这里只是简单示例，你可以自定义相关用户信息
 */
@Entity
@Table
@Data
public class WxAccount {

    @Id
    @GeneratedValue
    private Integer id;
    private String wxOpenid;
    private String sessionKey;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private Date lastTime;

}
