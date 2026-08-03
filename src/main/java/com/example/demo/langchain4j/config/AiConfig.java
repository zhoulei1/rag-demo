package com.example.demo.langchain4j.config;

import com.example.demo.langchain4j.component.CustomChatMemoryStore;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.onnx.bgesmallzhv15.BgeSmallZhV15EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Autowired
    private CustomChatMemoryStore customChatMemoryStore;

    @Bean
    public ChatMemoryProvider chatMemoryProvider(){

        return memoryId -> MessageWindowChatMemory
                .builder()
                .id(memoryId)
                //sys消息总是保留，包含user message和ai message（通常一条对话对应两条）
                .maxMessages(3)
                .chatMemoryStore(customChatMemoryStore)
                .build();
    }


    /*******向量配置begin******/
    @Bean
    public EmbeddingStore<TextSegment> inMemoryEmbeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

    @Bean
    public BgeSmallZhV15EmbeddingModel bgeSmallZhV15EmbeddingModel() {
        return  new BgeSmallZhV15EmbeddingModel();
    }

    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment>  inMemoryEmbeddingStore,BgeSmallZhV15EmbeddingModel  bgeSmallZhV15EmbeddingModel) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingModel(bgeSmallZhV15EmbeddingModel)
                .embeddingStore(inMemoryEmbeddingStore)     // 配置嵌入存储，用于检索文档向量
                .minScore(0.8)      // 最小相似度阈值
                .maxResults(5)      // 最大返回结果数
                .build();
    }

    /*******向量配置end******/

}
