package com.example.demo.langchain4j.service;

import com.example.demo.langchain4j.entity.ChatItem;
import com.example.demo.langchain4j.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ChatItemService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public ChatItem save(ChatItem chatItem) {
        chatItem.setChatId(UUID.randomUUID().toString().replace("-", ""));
        chatItem.setCreateTime(new Date());
        chatItem.setUpdateTime(new Date());
        return mongoTemplate.save(chatItem);
    }

    public List<ChatItem> list(String userId) {
        Criteria criteria = Criteria.where("userId").is(userId);
        Query query = Query.query(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        return mongoTemplate.find(query, ChatItem.class);
    }

    public ChatItem getByChatId(String chatId) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("chatId").is(chatId)),
                ChatItem.class);
    }

    public void updateItemName(String chatId, String itemName) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("chatId").is(chatId)),
                Update.update("itemName", itemName).set("updateTime", new Date()),
                ChatItem.class);
    }

    public void deleteByChatId(String chatId) {
        mongoTemplate.remove(
                Query.query(Criteria.where("chatId").is(chatId)),
                ChatItem.class);
    }

    public void touchUpdateTime(String chatId) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("chatId").is(chatId)),
                Update.update("updateTime", new Date()),
                ChatItem.class);
    }
}
