package ir.mhkapr.webtaxi.exception;

import jdk.jshell.spi.ExecutionControl;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(String message){
        super(message);
    }

}
