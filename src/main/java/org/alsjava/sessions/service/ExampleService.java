package com.purrbyte.aitools.service;

import com.purrbyte.aitools.command.GetDemoCommand;
import com.purrbyte.aitools.model.network.request.CreateDemoRequest;
import com.purrbyte.aitools.model.network.response.CreateDemoResponse;
import com.purrbyte.aitools.pattern.command.CommandBus;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Observed(name = "service.DemoService")
public class DemoService {

    private final CommandBus commandBus;

    public CreateDemoResponse create(CreateDemoRequest createDemoRequest) {
        return commandBus.sendCommand(GetDemoCommand.builder()
                .createDemoRequest(createDemoRequest)
                .build()
        );
    }
}
