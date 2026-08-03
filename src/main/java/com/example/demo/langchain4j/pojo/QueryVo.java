package com.example.demo.langchain4j.pojo;

import com.example.demo.langchain4j.enums.AiTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryVo {
    private AiTypeEnum aiType;
    private String message;
    private String chatId;
    private String userId;

}
