package com.example.demo.langchain4j.enums;

public enum AiTypeEnum {
    QIANWEN("qianwen", "qianwen"),
    DEEPSEEK("deepseek", "deepseek"),
    ;
    private String type;
    private String desc;

    AiTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }
}
