package com.wipro.communicationmanager.external;

import com.wipro.communicationmanager.Model;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
@Component
public class SmsServiceClientMock implements SmsServiceClient{

    @Override
    public CompletionStage<String> send(Model.SmsMetadata metadata) {
        return CompletableFuture.completedFuture("OK");
    }
}
