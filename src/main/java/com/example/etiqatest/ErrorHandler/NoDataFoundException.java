package com.example.etiqatest.ErrorHandler;

import com.example.etiqatest.commonconstant.ErrorConstant;

public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException() {
        super(ErrorConstant.NO_ENTITY_FOUND);
    }
}
