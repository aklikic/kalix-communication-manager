package com.wipro.communicationmanager.api;

import com.wipro.communicationmanager.Model;
import com.wipro.communicationmanager.external.MetadataServiceClient;
import com.wipro.communicationmanager.external.UserServiceClient;
import kalix.javasdk.action.Action;
import kalix.spring.KalixClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public class ApiControllerAction extends Action {

    private static Logger logger = LoggerFactory.getLogger(ApiControllerAction.class);
    private final KalixClient client;
    private final UserServiceClient userServiceClient;
    private final MetadataServiceClient metadataServiceClient;

    public ApiControllerAction(KalixClient client, UserServiceClient userServiceClient, MetadataServiceClient metadataServiceClient) {
        this.client = client;
        this.userServiceClient = userServiceClient;
        this.metadataServiceClient = metadataServiceClient;
    }

    @PostMapping("/send")
    public Action.Effect<String> send(@RequestBody SendRequest request){
        logger.info("send: {}",request);
        var user = userServiceClient.getUser(request.userId());
        var message = switch (request.channel){
            case EMAIL -> user.thenCompose( u ->  metadataServiceClient.getEmailMetadata(u, request.notificationType()))
                              .thenApply(m -> Model.ChannelMessage.email(request.userId(), m));
            case SMS -> user.thenCompose( u ->  metadataServiceClient.getSmsMetadata(u, request.notificationType()))
                            .thenApply(m -> Model.ChannelMessage.sms(request.userId(), m));
            case PUSH -> user.thenCompose( u ->  metadataServiceClient.getPushMetadata(u, request.notificationType()))
                             .thenApply(m -> Model.ChannelMessage.push(request.userId(), m));
        };
        var publish = message.thenCompose(m -> client.post("/internal/api/publish",m, Model.ChannelMessage.class).execute())
                             .thenApply(publishedMessage -> RESPONSE_OK).exceptionally(Throwable::getMessage);
        return effects().asyncReply(publish);
    }

    record SendRequest(String userId, Model.Channel channel, String notificationType){}
    public static String RESPONSE_OK = "OK";

}
