package com.zhimeng.caiwuweb.exception;

/**
 * Created by liupengfei on 2018/10/19 11:48
 */
public class TokenNullException extends RuntimeException {

   public TokenNullException(){

    }

    public TokenNullException(String msg){
        super(msg);
    }

}
