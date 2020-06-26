package com.zhimeng.caiwuweb.exception;

/**
 * Created by liupengfei on 2018/10/19 11:48
 */
public class AuthorityNotException extends RuntimeException {

   public AuthorityNotException(){

    }

    public AuthorityNotException(String msg){
        super(msg);
    }

}
