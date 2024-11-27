package com.hamroDaraz.daraz.exception;


import com.hamroDaraz.daraz.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
   // Catching resouncenotfoundException globally
    @ExceptionHandler(ResourceNotFoundException.class)//exception handler ani class ko name
    public ResponseEntity<?> ResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest)
    {
        ExceptionResponseDto exceptionResponseDto=new ExceptionResponseDto(webRequest.getDescription(false),HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }

    //Catching MethodArgumentNotValidException globally
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)  //catch(MethodArgumentNotValidException ex)block  le jastai kama garxa
    //or juna lekhenw ni eutai ho
//    public ResponseEntity<Map<String,String>>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)

    {
        Map<String,String> resp=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->
        {
            String filedName=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            resp.put(filedName,message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }


    //Catching any other exception globally
    //handle globalexception i.e juna sukai exception pani handle gardenxa if exception handle garana class haru or method pahila hainxa vanew
    @ExceptionHandler(Exception.class)//exception.class le baki raheko juna sukai exception lai ni handle gardenxa
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest webRequest)
    {
        ExceptionResponseDto exceptionResponseDto=new ExceptionResponseDto(webRequest.getDescription(false),HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDto);
    }
}
