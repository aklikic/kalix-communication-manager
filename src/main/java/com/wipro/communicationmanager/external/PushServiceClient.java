package com.wipro.communicationmanager.external;

import com.wipro.communicationmanager.Model;

import java.util.concurrent.CompletionStage;

public interface PushServiceClient {
    CompletionStage<String> send(Model.PushMetadata metadata);
}
