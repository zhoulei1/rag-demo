package com.example.demo.langchain4j.component;

import com.example.demo.langchain4j.enums.AiTypeEnum;
import dev.langchain4j.model.chat.StreamingChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ChatModelComponent {
    @Autowired
    @Qualifier("qwenStreamingChatModel")
    private StreamingChatModel qwenStreamingChatModel;

    @Autowired
    @Qualifier("deepseekStreamingChatModel")
    private StreamingChatModel deepseekStreamingChatModel;

    public StreamingChatModel getStreamingChatModel(AiTypeEnum aiType) {
        return switch (aiType) {
            case QIANWEN -> qwenStreamingChatModel;
            case DEEPSEEK -> deepseekStreamingChatModel;
        };
    }

}
