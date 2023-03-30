package com.wipro.communicationmanager.external;

import com.wipro.communicationmanager.Model;

import java.util.concurrent.CompletionStage;

public interface UserServiceClient {
    CompletionStage<Model.User> getUser(String userId);
}
