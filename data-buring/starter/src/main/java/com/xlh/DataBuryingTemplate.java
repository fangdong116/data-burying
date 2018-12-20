package com.xlh;

import org.springframework.amqp.core.AmqpTemplate;

/**
 * @author qingyang
 * @date 2018/12/19.
 */
public class DataBuryingTemplate {

    private AmqpTemplate amqpTemplate;

    private String routingKey;

    private String exchange;

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessage(Object message){
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }
}
