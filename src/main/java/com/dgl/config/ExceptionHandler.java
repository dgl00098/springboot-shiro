package com.dgl.config;

import com.dgl.common.Enum.CustomException;
import com.dgl.common.Enum.EnumErrorMsg;
import com.dgl.model.vo.RespEntity;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.elasticsearch.ElasticsearchStatusException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ValidationException;

/**
 * 全局异常拦截器
 */
@RestControllerAdvice
public class ExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(com.dgl.config.ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = IncorrectCredentialsException.class)
    public RespEntity<?> handleException(IncorrectCredentialsException t) {
        log.error("CustomException",t);
        return new RespEntity<>(EnumErrorMsg.PASSWORD_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = UnknownAccountException.class)
    public RespEntity<?> handleException(UnknownAccountException t) {
        log.error("CustomException",t);
        return new RespEntity<>(EnumErrorMsg.USER_ACCOUNT_NOT_EXIST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = CustomException.class)
    public RespEntity<?> handleException(CustomException t) {
        log.error("CustomException",t);
        return new RespEntity<>(t.getErrorCode(),t.getErrorMsg());
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(value = ElasticsearchStatusException.class)
    public RespEntity<?> exceptionHandler(ElasticsearchStatusException e){
        log.error("发生es状态原因是:",e);
        e.printStackTrace();
        return new RespEntity(EnumErrorMsg.DATA_NOT_EXIST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ConstraintViolationException.class)
    public RespEntity<?> exceptionHandler(ConstraintViolationException e){
        log.error("发生数据完整性违规异常！原因是:",e);
        e.printStackTrace();
        return new RespEntity(EnumErrorMsg.USER_ACCOUNT_HAS_EXIST);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
    public RespEntity<?> handle(ValidationException exception) {
        log.error("参数错误具体信息  信息验证失败：" + exception.getMessage(), exception);
        exception.printStackTrace();
        return new RespEntity(EnumErrorMsg.TEXT_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public RespEntity<?> handle(MethodArgumentNotValidException exception) {
        log.error("参数错误具体信息  方法参数验证失败：" + exception.getMessage(), exception);
        String defaultMessage = exception.getBindingResult().getFieldError().getDefaultMessage();
        return new RespEntity(EnumErrorMsg.TEXT_ERROR.getCode(),defaultMessage);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public RespEntity<?> handleRuntimeException(RuntimeException exception) {
        log.error("参数错误具体信息：" + exception.getMessage(), exception);
        exception.printStackTrace();
        return new RespEntity(EnumErrorMsg.SYS_BUSY);
    }


}
