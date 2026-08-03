package com.example.demo.langchain4j.service.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

/**
@AiService(
        //组件绑定模式：AUTOMATIC（自动查找）或 EXPLICIT（显式指定）
        wiringMode = EXPLICIT,
        //显式模式下指定 ChatLanguageModel Bean 的名称
        chatModel = "qwenChatModel",
        streamingChatModel = "qwenStreamingChatModel"

)
 */
public interface FluxChatService {

    @SystemMessage(fromResource = "phone-prompt-template.txt")//系统消息提示词
    Flux<String> chatStream( @MemoryId String chatId, @UserMessage String message);
}
