package com.anticaptcha;

public class AnticaptchaResult {

    public enum Status {
        ready,
        unknown, processing
    }

    private Integer errorId;
    private String errorCode;
    private String errorDescription;
    private Status status = Status.unknown;
    private String solution;
    private Double cost;
    private String ip;
    private Integer createTime;
    private Integer endTime;
    private Integer solveCount;

    public AnticaptchaResult(Status status, String solution, Integer errorId, String errorCode, String errorDescription, Double cost, String ip, Integer createTime, Integer endTime, Integer solveCount) {
        this.errorId = errorId;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.status = status;
        this.solution = solution;
        this.cost = cost;
        this.ip = ip;
        this.createTime = createTime;
        this.endTime = endTime;
        this.solveCount = solveCount;
    }

    @Override
    public String toString() {
        return "AnticaptchaResult{" +
                "errorId=" + errorId +
                ", errorCode='" + errorCode + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                ", status=" + status +
                ", solution='" + solution + '\'' +
                ", cost=" + cost +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", solveCount=" + solveCount +
                '}';
    }

    public Integer getErrorId() {
        return errorId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public Status getStatus() {
        return status;
    }

    public String getSolution() {
        return solution;
    }

    public Double getCost() {
        return cost;
    }

    public String getIp() {
        return ip;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public Integer getSolveCount() {
        return solveCount;
    }
}
