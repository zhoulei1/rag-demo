package com.example.demo.langchain4j.controller;

import com.example.demo.langchain4j.entity.ChatItem;
import com.example.demo.langchain4j.entity.ChatMessage;
import com.example.demo.langchain4j.pojo.QueryVo;
import com.example.demo.langchain4j.service.AiService;
import com.example.demo.langchain4j.service.ChatItemService;
import com.example.demo.langchain4j.service.ChatMessageService;
import dev.langchain4j.internal.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/ai")
@Slf4j
public class AIController {

    @Autowired
    private AiService aiService;
    @Autowired
    private ChatItemService chatItemService;
    @Autowired
    private ChatMessageService chatMessageService;


    @PostMapping(value = "/streamChat", produces = "text/event-stream;charset=utf-8")
    public Flux<String> streamChat(@RequestBody QueryVo queryVo) throws Exception {
        log.info("streamChat:{}", Json.toJson(queryVo));
        return aiService.chatStream(queryVo);

    }

    @GetMapping("/chatItems")
    public List<ChatItem> chatItems() {
        return chatItemService.list(getLoginUserId());
    }

    @PostMapping("/chatItem")
    public ChatItem createChatItem(@RequestBody ChatItem chatItem) {
        chatItem.setUserId(getLoginUserId());
        return chatItemService.save(chatItem);
    }

    @PutMapping("/chatItem")
    public void updateChatItem(@RequestBody ChatItem chatItem) {
        chatItemService.updateItemName(chatItem.getChatId(), chatItem.getItemName());
    }

    @DeleteMapping("/chatItem/{chatId}")
    public void deleteChatItem(@PathVariable("chatId") String chatId) {
        log.info("delete chatItem:{}",chatId);
        // 先删除会话下的消息，再删除会话
        chatMessageService.deleteByChatId(chatId);
        chatItemService.deleteByChatId(chatId);
    }

    @GetMapping("/chatMessages")
    public List<ChatMessage> chatMessages(@RequestParam("chatId") String chatId) {
        return chatMessageService.listByChatId(chatId);
    }

    private String getLoginUserId() {
        return "1";
    }

}
