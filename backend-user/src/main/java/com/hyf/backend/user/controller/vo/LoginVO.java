package com.hyf.backend.user.controller.vo;

import com.hyf.backend.utils.common.vo.BaseRequestVO;
import com.hyf.backend.utils.exception.BizException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Elvis on 2020/2/14
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
public class LoginVO extends BaseRequestVO {
    private String username;
    private String password;

    @Override
    public void checkParam() throws BizException {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new BizException(404, "用户名或密码不能为空");
        }
    }
}
