package com.wipro.communicationmanager.external;

import com.wipro.communicationmanager.Model;
import com.wipro.communicationmanager.api.ApiControllerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class UserServiceClientMock implements UserServiceClient{
    private static Logger logger = LoggerFactory.getLogger(UserServiceClientMock.class);
    private static final List<Model.User> users =
            IntStream.range(1,10).mapToObj(i -> new Model.User(i+"","Name#%s".formatted(i),"1@email.com","01123450%s".formatted(i), "99999%s".formatted(i))).collect(Collectors.toList());

    @Override
    public CompletionStage<Model.User> getUser(String userId) {
        var maybeUser = users.stream().filter(u -> u.userId().equals(userId)).findFirst();
        logger.info("getUser: {}: {}",userId,maybeUser);
        CompletableFuture<Model.User> fail = new CompletableFuture<>();
        fail.completeExceptionally(new RuntimeException("User not found!"));
        return maybeUser.map(CompletableFuture::completedFuture).orElse(fail);
    }
}
