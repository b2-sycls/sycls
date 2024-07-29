package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class CastNotFoundException extends GlobalNotFoundException {

    public CastNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}