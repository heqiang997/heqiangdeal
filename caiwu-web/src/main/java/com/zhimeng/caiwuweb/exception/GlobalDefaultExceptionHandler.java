package com.zhimeng.caiwuweb.exception;

import com.zhimeng.caiwuweb.dto.JsonData;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * Created by liupengfei on 2018/10/19 10:28
 */
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public JsonData handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
        return new JsonData(400,"缺少参数");

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonData handleMethodArgumentNotValidException(MissingServletRequestParameterException e){

        return new JsonData(403,"访问被拒绝");

    }

    @ExceptionHandler(TokenNullException.class)
    public JsonData exception(TokenNullException e){

        return new JsonData(401,e.getMessage());
    }

    @ExceptionHandler(AuthorityNotException.class)
    public JsonData exception(AuthorityNotException e){

        return new JsonData(406,e.getMessage());

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public JsonData exception(MethodArgumentTypeMismatchException e){

        return new JsonData(405,"参数异常");

    }

    @ExceptionHandler(NullPointerException.class)
    public JsonData exception(NullPointerException e){
        e.getMessage();
        e.printStackTrace();
        return new JsonData(407,"参数不能为空");

    }

    @ExceptionHandler(DateUtilException.class)
    public JsonData exception(DateUtilException e){

        return new JsonData(408,e.getMessage());

    }

    @ExceptionHandler(IllegalStateException.class)
    public JsonData exception(IllegalStateException e){

        return new JsonData(408,"文件内容错误！");

    }

    @ExceptionHandler(FileInputException.class)
    public JsonData exception(FileInputException e){

        return new JsonData(408,e.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public JsonData exception(Exception e){
        return new JsonData(410,"网络异常！");

    }

    @ExceptionHandler(TooManyResultsException.class)
    public JsonData exception(TooManyResultsException e){

        return new JsonData(409,"用户昵称重复");

    }
}