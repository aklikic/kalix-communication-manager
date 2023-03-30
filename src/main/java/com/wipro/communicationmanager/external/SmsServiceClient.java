package com.wipro.communicationmanager.external;

import com.wipro.communicationmanager.Model;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface SmsServiceClient {
    CompletionStage<String> send(Model.SmsMetadata metadata);
}
