package com.example.etiqatest.ErrorHandler;

import com.example.etiqatest.commonconstant.ErrorConstant;

public class CourseExceptionError extends RuntimeException {

    public CourseExceptionError() {
        super(ErrorConstant.COURSE_NOT_ASSIGNED);
    }
}
