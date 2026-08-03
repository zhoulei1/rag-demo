package com.example.demo.langchain4j.config;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class AiModelConfig {

    @Value("${deepseek.api-key}")
    private String deepSeekApiKey;
    @Value("${deepseek.model-name:deepseek-chat}")
    private String deepSeekModelName;
    @Value("${deepseek.base-url:https://api.deepseek.com}")
    private String deepSeekBaseUrl;

    @Value("${qianwen.api-key}")
    private String qianwenApiKey;
    @Value("${qianwen.model-name:qwen3.7-flash-2026-07-15}")
    private String qianwenModelName;
    @Value("${qianwen.base-url:https://dashscope.aliyuncs.com/compatible-mode/v1}")
    private String qianwenBaseUrl;

    @Bean
    public StreamingChatModel deepseekStreamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .baseUrl(deepSeekBaseUrl)
                .apiKey(deepSeekApiKey)
                .modelName(deepSeekModelName)
                .timeout(Duration.ofSeconds(120))
                .build();
    }

    @Bean
    public StreamingChatModel qwenStreamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .baseUrl(qianwenBaseUrl)
                .apiKey(qianwenApiKey)
                .modelName(qianwenModelName)
                .timeout(Duration.ofSeconds(120))
                .build();
    }
}
