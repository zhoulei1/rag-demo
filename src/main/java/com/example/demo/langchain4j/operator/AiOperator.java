package com.example.demo.langchain4j.operator;

import com.example.demo.langchain4j.factory.AiServiceFactory;
import com.example.demo.langchain4j.pojo.QueryVo;
import com.example.demo.langchain4j.service.ai.FluxChatService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

public abstract class AiOperator {
    @Autowired
    private AiServiceFactory aiServiceFactory;

    public Flux<String> chatStream(QueryVo queryVo) throws Exception {
        FluxChatService fluxChatService = aiServiceFactory.createAiService(queryVo.getAiType());
        return fluxChatService.chatStream(queryVo.getChatId(),queryVo.getMessage());
    }
}
