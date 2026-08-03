package com.example.demo.langchain4j.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;


@Data
@NoArgsConstructor
@Document("chatItem")
public class ChatItem {

    private String itemId;
    private String userId;
    private String chatId;
    private String itemName;
    private Date createTime;
    private Date updateTime;
}
