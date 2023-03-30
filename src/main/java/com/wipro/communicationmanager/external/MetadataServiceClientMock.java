package com.wipro.communicationmanager.external;

import com.wipro.communicationmanager.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
public class MetadataServiceClientMock implements MetadataServiceClient{
    private static Logger logger = LoggerFactory.getLogger(MetadataServiceClientMock.class);
    @Override
    public CompletionStage<Model.EmailMetadata> getEmailMetadata(Model.User user, String notificationType) {
        var metadata = new Model.EmailMetadata(
                user.email(),
                "subject for %s".formatted(notificationType),
                "some body for %s".formatted(notificationType),
                "%s-%s-myattach.pdf".formatted(user.userId(),notificationType));
        logger.info("getEmailMetadata: {}",metadata);
        return CompletableFuture.completedFuture(metadata);
    }

    @Override
    public CompletionStage<Model.SmsMetadata> getSmsMetadata(Model.User user, String notificationType) {
        var metadata = new Model.SmsMetadata(
                user.smsNumber(),
                "body for %s".formatted(notificationType));
        logger.info("getSmsMetadata: {}",metadata);
        return CompletableFuture.completedFuture(metadata);
    }

    @Override
    public CompletionStage<Model.PushMetadata> getPushMetadata(Model.User user, String notificationType) {
        var metadata = new Model.PushMetadata(
                user.pushToken(),
                "body for %s".formatted(notificationType));
        logger.info("getPushMetadata: {}",metadata);
        return CompletableFuture.completedFuture(metadata);
    }
}
