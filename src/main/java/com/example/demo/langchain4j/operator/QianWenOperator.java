package com.example.demo.langchain4j.operator;


import com.example.demo.langchain4j.annotation.BeanType;
import com.example.demo.langchain4j.enums.AiTypeEnum;
import com.example.demo.langchain4j.pojo.QueryVo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@BeanType(AiTypeEnum.QIANWEN)
public class QianWenOperator extends AiOperator{

    public Flux<String> chatStream(QueryVo queryVo) throws Exception {
      return super.chatStream(queryVo);
    }
}
