package com.dgl.common.Enum;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class CustomException extends RuntimeException {


    private String errorCode;

    private String errorMsg;

    public CustomException(String errorCode, String errorMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    public CustomException(EnumErrorMsg enumErrorMsg) {
        super();
        this.errorCode = enumErrorMsg.getCode();
        this.errorMsg = enumErrorMsg.getMsg();
    }

    public CustomException(JSONObject jsonObject) {
        this.errorCode = jsonObject.getString("code");
        this.errorMsg = jsonObject.getString("msg");
    }
}
