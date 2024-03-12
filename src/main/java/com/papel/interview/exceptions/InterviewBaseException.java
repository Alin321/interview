package com.papel.interview.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InterviewBaseException extends RuntimeException {

    private ErrorCode code;
}
