package com.wipro.communicationmanager.external;

import com.wipro.communicationmanager.Model;

import java.util.concurrent.CompletionStage;

public interface EmailServiceClient {
    CompletionStage<String> send(Model.EmailMetadata metadata);
}
