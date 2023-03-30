package com.wipro.communicationmanager.channel;

import com.wipro.communicationmanager.Model;
import com.wipro.communicationmanager.external.EmailServiceClient;
import com.wipro.communicationmanager.external.PushServiceClient;
import com.wipro.communicationmanager.external.SmsServiceClient;
import kalix.javasdk.action.Action;
import kalix.javasdk.annotations.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Subscribe.Topic("channel-topic")
public class ChannelSenderAction extends Action {

    private static Logger logger = LoggerFactory.getLogger(ChannelSenderAction.class);
    private final EmailServiceClient emailClient;
    private final SmsServiceClient smsServiceClient;
    private final PushServiceClient pushServiceClient;

    public ChannelSenderAction(EmailServiceClient emailClient, SmsServiceClient smsServiceClient, PushServiceClient pushServiceClient) {
        this.emailClient = emailClient;
        this.smsServiceClient = smsServiceClient;
        this.pushServiceClient = pushServiceClient;
    }

    public Effect<String> process(Model.ChannelMessage message){
        logger.info("process: {}",message);
        var send = switch (message.channel()){
            case EMAIL -> emailClient.send(message.emailMetadata());
            case SMS -> smsServiceClient.send(message.smsMetadata());
            case PUSH -> pushServiceClient.send(message.pushMetadata());
        };
        return effects().asyncReply(send);
    }
}
