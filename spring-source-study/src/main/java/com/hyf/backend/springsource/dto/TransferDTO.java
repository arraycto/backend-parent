package com.hyf.backend.springsource.dto;

import lombok.Data;

/**
 * @Author: Elvis on 2020/3/29
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
public class TransferDTO {
    private Integer fromUserId;
    private Integer toUserId;
    private Integer amount;
}
