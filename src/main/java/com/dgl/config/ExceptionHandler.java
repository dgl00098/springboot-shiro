package com.dgl.config;

import com.dgl.common.Enum.CustomException;
import com.dgl.common.Enum.EnumErrorMsg;
import com.dgl.smodel.response.RespEntity;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ValidationException;

/**
 * 全局异常拦截器
 */
@RestControllerAdvice
public class ExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(com.dgl.config.ExceptionHandler.class);


    @org.springframework.web.bind.annotation.ExceptionHandler(value = CustomException.class)
    public RespEntity<?> handleException(CustomException t) {
        CustomException e = t;
        e.printStackTrace();
        log.error("CustomException");
        log.error(e.getMessage(), e);
        return new RespEntity<>(e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value =NullPointerException.class)
    public RespEntity<?> exceptionHandler(NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        e.printStackTrace();
        return new RespEntity(EnumErrorMsg.OP_FAILED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = DataIntegrityViolationException.class)
    public RespEntity<?> exceptionHandler(DataIntegrityViolationException e){
        log.error("发生数据完整性违规异常！原因是:",e);
        e.printStackTrace();
        return new RespEntity(EnumErrorMsg.OP_FAILED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = MySQLIntegrityConstraintViolationException.class)
    public RespEntity<?> exceptionHandler(MySQLIntegrityConstraintViolationException e){
        log.error("发生数据完整性违规异常！原因是:",e);
        e.printStackTrace();
        return new RespEntity(EnumErrorMsg.OP_FAILED);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(BadSqlGrammarException.class)
    public RespEntity<?> sqlGrammarError(BadSqlGrammarException e){
        log.info("参数错误具体信息：" + e.getMessage());
        return new RespEntity<>(EnumErrorMsg.OP_FAILED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
    public RespEntity<?> handle(ValidationException exception) {
        log.error("参数错误具体信息：" + exception.getMessage(), exception);
        exception.printStackTrace();
        return new RespEntity(EnumErrorMsg.TEXT_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public RespEntity<?> handle(MethodArgumentNotValidException exception) {
        log.error("参数错误具体信息：" + exception.getMessage(), exception);
        exception.printStackTrace();
        String defaultMessage = exception.getBindingResult().getFieldError().getDefaultMessage();
        return new RespEntity(defaultMessage);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public RespEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("参数错误具体信息：" + exception.getMessage(), exception);
        exception.printStackTrace();
        return new RespEntity(EnumErrorMsg.TEXT_ERROR);
    }


}
