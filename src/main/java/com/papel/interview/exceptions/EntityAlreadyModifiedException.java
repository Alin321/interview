package com.papel.interview.exceptions;

import static com.papel.interview.exceptions.ErrorCode.*;

public class EntityAlreadyModifiedException extends InterviewBaseException {
    public EntityAlreadyModifiedException() {
        super(INT_0003);
    }
}
