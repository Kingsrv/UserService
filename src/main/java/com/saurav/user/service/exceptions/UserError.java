package com.saurav.user.service.exceptions;

import lombok.Getter;

@Getter
public abstract class UserError {
    protected final String userId;
    protected final String description;
    protected final UserErrorType errorType;

    public UserError(String userId, UserErrorType errorType, String description){
        this.userId = userId;
        this.errorType = errorType;
        this.description = description;
    }


}
