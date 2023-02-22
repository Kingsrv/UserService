package com.saurav.user.service.exceptions;

import lombok.AccessLevel;
import lombok.Setter;

@Setter(AccessLevel.NONE)
public class ErrorOrSuccess<T> {

    private final UserError error;
    private final T success;

    public ErrorOrSuccess(UserError error, T success) {
        this.error = error;
        this.success = success;
    }

    public static <T> ErrorOrSuccess<T> error(UserError error) {
        return new ErrorOrSuccess<>(error, null);
    }

    public static <T> ErrorOrSuccess<T> success(T success) {
        return new ErrorOrSuccess<>(null, success);
    }

}
