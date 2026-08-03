package com.example.demo.langchain4j.service;

import com.example.demo.langchain4j.entity.ChatItem;
import com.example.demo.langchain4j.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ChatMessageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setMessageId(UUID.randomUUID().toString().replace("-", ""));
        chatMessage.setCreateTime(new Date());
        return mongoTemplate.save(chatMessage);
    }

    public List<ChatMessage> listByChatId(String chatId) {
        Criteria criteria = Criteria.where("chatId").is(chatId);
        Query query = Query.query(criteria);
        query.with(Sort.by(Sort.Direction.ASC, "createTime"));
        return mongoTemplate.find(query, ChatMessage.class);
    }

    public void deleteByChatId(String chatId) {
        mongoTemplate.remove(
                Query.query(Criteria.where("chatId").is(chatId)),
                ChatMessage.class);
    }
}
