package com.example.demo.langchain4j.component;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.apache.poi.ApachePoiDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.bgesmallzhv15.BgeSmallZhV15EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VectorStoreComponent {
    @Autowired
    private EmbeddingStore<TextSegment> inMemoryEmbeddingStore;
    @Autowired
    private BgeSmallZhV15EmbeddingModel bgeSmallZhV15EmbeddingModel;
    @Autowired
    private ResourceLoader resourceLoader;


    @PostConstruct
    public void initVectorStore() throws Exception{
        log.info("iniInMemoryEmbeddingStore begin");
        iniInMemoryEmbeddingStore();
        log.info("iniInMemoryEmbeddingStore end");

    }
    private void iniInMemoryEmbeddingStore()throws Exception{
        DocumentSplitter documentSplitter = DocumentSplitters.recursive(300, 50);
        EmbeddingStoreIngestor embeddingStoreIngestor = EmbeddingStoreIngestor.builder()
                // 向量存储
                .embeddingStore(inMemoryEmbeddingStore)
                // 向量化模型
                .embeddingModel(bgeSmallZhV15EmbeddingModel)
                // 文本分割器
                .documentSplitter(documentSplitter)
                .build();


        Resource resource = resourceLoader.getResource("classpath:embedding/阿里云百炼系列手机产品介绍.docx");
       // Path filePath = Paths.get(resource.getFile().getAbsolutePath());
       // Document document = FileSystemDocumentLoader.loadDocument(filePath);

        ApachePoiDocumentParser apachePoiDocumentParser = new ApachePoiDocumentParser();
        Document document = apachePoiDocumentParser.parse(resource.getInputStream());
        log.info("iniInMemoryEmbeddingStore text:{}",document.text());
        embeddingStoreIngestor.ingest(document);
    }
}
