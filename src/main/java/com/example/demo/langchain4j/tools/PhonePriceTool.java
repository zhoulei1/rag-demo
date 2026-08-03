package com.example.demo.langchain4j.tools;


import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PhonePriceTool {
    @Tool(name = "查询手机去年最低价格",value="查到的结果直接返回给用户，参数phonName为手机型号名称，如阿里云百炼X1")
    public Double queryLastYearMinPrice(String phoneName) {
        Double price = phoneName.equals("阿里云百炼X1") ? null:phoneName.hashCode()*1d;
        log.info("queryPreviousPrice:{},去年价格：{}",phoneName,price);
        return price;
    }

}
