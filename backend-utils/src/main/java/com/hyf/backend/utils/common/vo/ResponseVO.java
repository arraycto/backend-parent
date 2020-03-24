package com.hyf.backend.utils.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hyf.backend.utils.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Elvis on 2020/2/13
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ResponseVO<T> {

    private int code;
    private String msg;
    private T data;

    public ResponseVO(T data) {
        this.data = data;
        this.code = 0;
        this.msg = "success";
    }
    @JsonIgnore
    public Boolean isOk(){
        return code == 0 && data != null;
    }
    public static ResponseVO<?> ok() {
        ResponseVO<?> responseVO = new ResponseVO<>();
        responseVO.setCode(0);
        responseVO.setMsg("success");
        return responseVO;
    }

    public static <T> ResponseVO<T> ok(T data) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setCode(0);
        responseVO.setMsg("success");
        responseVO.setData(data);
        return responseVO;
    }

    public static ResponseVO error(int code, String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(code);
        responseVO.setMsg(msg);
        return responseVO;
    }
    public static ResponseVO<String> error(String msg) {
        ResponseVO<String> responseVO = new ResponseVO<>();
        responseVO.setCode(-1);
        responseVO.setMsg(msg);
        return responseVO;
    }

    public static ResponseVO<?> error() {
        ResponseVO<?> responseVO = new ResponseVO<>();
        responseVO.setCode(-1);
        responseVO.setMsg("未知错误");
        return responseVO;
    }

    // 未登录异常
    public static <T> ResponseVO<?> noLogin() {
        ResponseVO<?> response = new ResponseVO<>();
        response.setCode(401);
        response.setMsg("请登录");
        return response;
    }

    // 出现业务异常
    public static <T> ResponseVO<?> serviceException(BizException e) {
        ResponseVO<?> response = new ResponseVO<>();
        response.setCode(e.getCode());
        response.setMsg(e.getMessage());
        return response;
    }
}
