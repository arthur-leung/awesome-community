package com.arthur.awesome.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean exist(Byte type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType() == (int)type) {
                return false;
            }
        }

        return true;
    }

    public Integer getType() {
        return type;
    }
}
