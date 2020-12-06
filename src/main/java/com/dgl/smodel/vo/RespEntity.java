package com.dgl.smodel.vo;

import com.dgl.common.Enum.EnumErrorMsg;
import lombok.Data;

@Data
public class RespEntity<T> {

    private String code;

    private String message;

    private T data;


    public RespEntity() {
        this.code = EnumErrorMsg.OP_SUCCESS.getCode();
        this.message = EnumErrorMsg.OP_SUCCESS.getMsg();
    }

    public RespEntity(EnumErrorMsg enumErrorMsg) {
        this.code = enumErrorMsg.getCode();
        this.message = enumErrorMsg.getMsg();
        this.data=null;
    }

    public RespEntity(T data) {
        this.code = EnumErrorMsg.OP_SUCCESS.getCode();
        this.message = EnumErrorMsg.OP_SUCCESS.getMsg();
        this.data = data;
    }

    public RespEntity(String code, String message) {
        this.code=code;
        this.message=message;
        this.data=null;
    }

    public RespEntity(T data, String code, String message) {
        this.code=code;
        this.message=message;
        this.data=data;
    }


    public static RespEntity failed(String message) {
        RespEntity respEntity = new RespEntity(EnumErrorMsg.SYS_BUSY.getCode(),message);
        return respEntity;
    }

    public static RespEntity failed(EnumErrorMsg enumErrorMsg) {
        RespEntity respEntity = new RespEntity(enumErrorMsg.getCode(),enumErrorMsg.getMsg());
        return respEntity;
    }

    public static RespEntity success(Object data) {
        RespEntity respEntity = new RespEntity(data,EnumErrorMsg.OP_SUCCESS.getCode(),EnumErrorMsg.OP_SUCCESS.getMsg());
        return respEntity;
    }

}
