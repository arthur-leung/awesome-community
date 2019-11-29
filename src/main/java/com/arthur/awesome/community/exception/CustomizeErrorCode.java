package com.arthur.awesome.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    SYSTEM_ERROR(400, "系统异常"),
    QUESTION_NOT_FOUND(2001, "你找的问题找不到了，你要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2001, "未选择问题或评论进行回复"),
    TYPE_PARAM_NOT_FOUND(2001, "评论类型非法"),
    NOT_FOUND_COMMENT(2001, "被评论的评论不存在"),
    NO_LOGIN(2002, "未登录不能进行评论，请先登录");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
