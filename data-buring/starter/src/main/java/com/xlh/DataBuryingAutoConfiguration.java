package com.xlh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qingyang
 * @date 2018/12/19.
 */
@Configuration
@ConditionalOnBean(AmqpTemplate.class)
@AutoConfigureAfter(RabbitAutoConfiguration.class)
@ConditionalOnProperty(prefix = "data.buring", value = "exchange")
@Slf4j
public class DataBuryingAutoConfiguration {


    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = "data.buring")
    @Bean
    public DataBuryingTemplate dataBuringTemplate(AmqpTemplate amqpTemplate){
        DataBuryingTemplate dataBuryingTemplate = new DataBuryingTemplate();
        dataBuryingTemplate.setAmqpTemplate(amqpTemplate);
        return dataBuryingTemplate;
    }
}
