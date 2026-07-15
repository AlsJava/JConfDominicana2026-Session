package org.alsjava.sessions.service;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.alsjava.sessions.command.Sum2NumbersCommand;
import org.alsjava.sessions.model.network.request.Sum2NumbersRequest;
import org.alsjava.sessions.model.network.response.Sum2NumbersResponse;
import org.alsjava.sessions.pattern.command.CommandBus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Observed(name = "service.ExampleService")
public class ExampleService {

    private final CommandBus commandBus;

    public Sum2NumbersResponse sum2Numbers(Sum2NumbersRequest sum2NumbersRequest) {
        return commandBus.sendCommand(Sum2NumbersCommand.builder()
                .sum2NumbersRequest(sum2NumbersRequest)
                .build()
        );
    }
}
