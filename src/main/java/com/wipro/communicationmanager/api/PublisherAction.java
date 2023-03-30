package com.wipro.communicationmanager.api;

import com.wipro.communicationmanager.Model;
import kalix.javasdk.Metadata;
import kalix.javasdk.action.Action;
import kalix.javasdk.annotations.Publish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/internal/api")
public class PublisherAction extends Action {
    private static Logger logger = LoggerFactory.getLogger(PublisherAction.class);
    @PostMapping("/publish")
    @Publish.Topic("channel-topic")
    public Effect<Model.ChannelMessage> publish(@RequestBody Model.ChannelMessage message){
        logger.info("publish: {}",message);
        return effects().reply(message, Metadata.EMPTY.add("ce-subject","%s:%s".formatted(message.channel(),message.userId()))); //ce-subject header is used as partition key
    }

}
