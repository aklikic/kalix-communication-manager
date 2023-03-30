package com.wipro.communicationmanager.external;

import com.wipro.communicationmanager.Model;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
@Component
public class PushServiceClientMock implements PushServiceClient{

    @Override
    public CompletionStage<String> send(Model.PushMetadata metadata) {
        return CompletableFuture.completedFuture("OK");
    }
}
