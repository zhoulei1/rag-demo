package com.example.demo.langchain4j.service;


import com.example.demo.langchain4j.annotation.BeanType;
import com.example.demo.langchain4j.entity.ChatMessage;
import com.example.demo.langchain4j.operator.AiOperator;
import com.example.demo.langchain4j.pojo.QueryVo;
import dev.langchain4j.data.message.ChatMessageType;
import dev.langchain4j.internal.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class AiService implements ApplicationContextAware {
    @Autowired
    private ChatMessageService chatMessageService;

    // 策略模式 的 bean容器
    private final Map<String, AiOperator> MAP = new ConcurrentHashMap<>();
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(BeanType.class);
        if (MapUtils.isEmpty(beanMap)) {
            return;
        }
        // 遍历放入Map集合
        beanMap.values().forEach(bean -> {
            BeanType beanType = bean.getClass().getAnnotation(BeanType.class);
            String model = beanType.value().getType();
            MAP.put(model, (AiOperator) bean);
        });
    }

    private AiOperator getAiOperator(String type) {
        return MAP.get(type);
    }



    public Flux<String> chatStream(QueryVo queryVo) throws Exception {
        // 1. 保存用户消息
        ChatMessage userMsg = new ChatMessage();
        userMsg.setChatId(queryVo.getChatId());
        userMsg.setUserId(queryVo.getUserId());
        userMsg.setMessageText(queryVo.getMessage());
        userMsg.setChatMessageType(ChatMessageType.USER);
        log.info("chatStream save userMessage:{}", Json.toJson(userMsg));
        chatMessageService.save(userMsg);

        // 2. 流式调用 + 收集全文后保存 AI 消息
        StringBuilder fullResponse = new StringBuilder();
        Flux<String> result = this.getAiOperator(queryVo.getAiType().getType()).chatStream(queryVo)
                .doOnNext(fullResponse::append)
                .doFinally(signalType -> {
                    String aiText = fullResponse.toString();
                    log.info("streamChat 完成, signal={}, aiText长度={}", signalType, aiText.length());
                    if (!aiText.isEmpty()) {
                        ChatMessage aiMsg = new ChatMessage();
                        aiMsg.setChatId(queryVo.getChatId());
                        aiMsg.setUserId(queryVo.getUserId());
                        aiMsg.setMessageText(aiText);
                        aiMsg.setChatMessageType(ChatMessageType.AI);
                        log.info("chatStream save aiMessage:{}", Json.toJson(aiMsg));
                        chatMessageService.save(aiMsg);
                    }
                });
        return result;
    }
}
