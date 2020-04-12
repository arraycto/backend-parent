package com.hyf.backend.user.controller.vo;

import com.hyf.backend.user.entity.MoocBackendUserT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Elvis on 2020/3/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthVO implements Serializable {

    private static final long serialVersionUID = -8716153725287615463L;
    private Integer id;

    private String username;

    private Byte gender;

    private Date birthday;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Byte userLevel;

    private String nickName;

    private String mobile;

    private String avatar;

    private String token;

    public UserAuthVO(MoocBackendUserT userT) {
        BeanUtils.copyProperties(userT, this);
    }
}
