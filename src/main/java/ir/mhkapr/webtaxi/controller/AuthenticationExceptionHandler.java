package ir.mhkapr.webtaxi.controller;

import io.jsonwebtoken.ExpiredJwtException;
import ir.mhkapr.webtaxi.exception.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthenticationExceptionHandler {
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        Map<String,Object> errorInfo = new HashMap<>();
        errorInfo.put("message" , ex.getMessage());
        return new ResponseEntity<>(errorInfo,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
