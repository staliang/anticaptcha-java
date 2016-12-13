package com.anticaptcha;

class AnticaptchaTask {

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

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public Integer getErrorId() {
        return errorId;
    }

    @Override
    public String toString() {
        return "AnticaptchaTask{" +
                "errorId=" + errorId +
                ", taskId=" + taskId +
                ", errorCode='" + errorCode + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}
