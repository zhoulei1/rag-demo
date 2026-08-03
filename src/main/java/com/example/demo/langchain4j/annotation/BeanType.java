package com.example.demo.langchain4j.annotation;

import com.example.demo.langchain4j.enums.AiTypeEnum;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface BeanType {

    AiTypeEnum value() default AiTypeEnum.QIANWEN;
}