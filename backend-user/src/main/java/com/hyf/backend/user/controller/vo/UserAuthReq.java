package com.hyf.backend.user.controller.vo;

import com.hyf.backend.user.dto.UserAuthDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Elvis on 2020/3/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
public class UserAuthReq implements Serializable {


    private static final long serialVersionUID = 8082419186063858074L;

    @NotNull(message = "用户名不能为空")
    private String userName;
    @NotNull(message = "密码不能为空")
    private String userPwd;

    public UserAuthDTO toDTO() {
        UserAuthDTO authDTO = new UserAuthDTO();
        BeanUtils.copyProperties(this, authDTO);
        return authDTO;
    }
}
