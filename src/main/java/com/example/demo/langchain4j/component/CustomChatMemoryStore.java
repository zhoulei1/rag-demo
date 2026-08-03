package com.example.demo.langchain4j.component;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
/*@See SingleSlotChatMemoryStore实现*/
public class CustomChatMemoryStore implements ChatMemoryStore {

    /**按需持久化存储*/
    private final Map<Object, List<ChatMessage>> messagesByMemoryId = new ConcurrentHashMap<>();

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        List<ChatMessage> chatMessages = messagesByMemoryId.computeIfAbsent(memoryId, ignored -> new ArrayList<>());
        log.info("chatMemory getMessages:{},{}", memoryId,chatMessages);
        return chatMessages;
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        log.info("chatMemory updateMessages:{},{}", memoryId,messages);
        messagesByMemoryId.put(memoryId, messages);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        log.info("chatMemory deleteMessages:{}",memoryId);
        messagesByMemoryId.remove(memoryId);
    }
}
