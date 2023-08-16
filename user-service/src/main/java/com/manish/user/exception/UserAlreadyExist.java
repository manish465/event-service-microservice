package com.manish.user.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String message){
        super(message);
    }
}
