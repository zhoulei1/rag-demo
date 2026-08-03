package com.example.demo.langchain4j.factory;

import com.example.demo.langchain4j.service.ai.FluxChatService;
import com.example.demo.langchain4j.enums.AiTypeEnum;
import com.example.demo.langchain4j.component.ChatModelComponent;
import com.example.demo.langchain4j.tools.PhonePriceTool;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AiServiceFactory {

    @Autowired
    private ChatModelComponent chatModelUtil;
    @Autowired
    private ContentRetriever contentRetriever;
    @Autowired
    private ChatMemoryProvider chatMemoryProvider;
    @Autowired
    private PhonePriceTool phonePriceTool;

    public FluxChatService createAiService(AiTypeEnum aiTypeEnum) throws Exception {
       return AiServices.builder(FluxChatService.class)
                .streamingChatModel(chatModelUtil.getStreamingChatModel(aiTypeEnum))
                .chatMemoryProvider(chatMemoryProvider)
                .contentRetriever(contentRetriever)
                .tools(phonePriceTool)
                .build();
    }
}
