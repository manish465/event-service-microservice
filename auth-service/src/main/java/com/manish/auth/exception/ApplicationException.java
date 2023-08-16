package com.manish.auth.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationException extends RuntimeException{
    public ApplicationException(String message){
        super(message);
    }
}
