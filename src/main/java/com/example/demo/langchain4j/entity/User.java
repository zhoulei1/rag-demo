package com.example.demo.langchain4j.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("chatItem")
public class User {
    private String userId;
    private String userName;
    private String pwd;
}
