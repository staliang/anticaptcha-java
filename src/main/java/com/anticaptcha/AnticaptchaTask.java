package com.anticaptcha;

import lombok.Data;

@Data
public class AnticaptchaTask {
    private Integer errorId;
    private Integer taskId;
    private String errorCode;
    private String errorDescription;
    private AnticaptchaResult result;

    AnticaptchaTask(Integer taskId, Integer errorId, String errorCode, String errorDescription) {
        this.errorId = errorId;
        this.taskId = taskId;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}
