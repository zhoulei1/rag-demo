package com.example.demo.langchain4j.entity;

import dev.langchain4j.data.message.ChatMessageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document("chatMessage")
public class ChatMessage {

    private String messageId;
    private String chatId;
    private String userId;
    private String messageText;
    private ChatMessageType chatMessageType;
    private Date createTime;
}
