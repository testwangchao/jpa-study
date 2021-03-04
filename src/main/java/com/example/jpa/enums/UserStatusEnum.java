package com.example.jpa.enums;


/**
 * 用户状态
 */
public enum  UserStatusEnum implements ValueEnum<Integer>{

    IS_ACTIVE(1, "已激活"),
    NOT_ACTIVE(2, "未激活");

    private final int code;
    private final String desc;

    UserStatusEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }
}
