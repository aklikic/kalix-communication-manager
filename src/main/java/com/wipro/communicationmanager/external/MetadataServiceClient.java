package com.wipro.communicationmanager.external;

import com.wipro.communicationmanager.Model;

import java.util.concurrent.CompletionStage;

public interface MetadataServiceClient {

    CompletionStage<Model.EmailMetadata> getEmailMetadata(Model.User user, String notificationType);
    CompletionStage<Model.SmsMetadata> getSmsMetadata(Model.User user, String notificationType);
    CompletionStage<Model.PushMetadata> getPushMetadata(Model.User user, String notificationType);
}
