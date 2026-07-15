package com.purrbyte.aitools.command.handler;

import com.purrbyte.aitools.command.GetDemoCommand;
import com.purrbyte.aitools.model.DemoDto;
import com.purrbyte.aitools.model.network.request.CreateDemoRequest;
import com.purrbyte.aitools.model.network.response.CreateDemoResponse;
import com.purrbyte.aitools.pattern.command.CommandEvent;
import com.purrbyte.aitools.pattern.command.CommandHandler;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Slf4j
@Component
@CommandEvent(command = GetDemoCommand.class)
@Observed(name = "command.GetDemoCommand")
public class GetDemoCommandHandler implements CommandHandler<CreateDemoResponse, GetDemoCommand> {

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public CreateDemoResponse handle(GetDemoCommand command) {
        CreateDemoRequest createDemoRequest = command.getCreateDemoRequest();
        DemoDto demoDto = DemoDto.builder()
                .id(secureRandom.nextLong())
                .name(createDemoRequest.getName())
                .description(createDemoRequest.getDescription())
                .build();
        return CreateDemoResponse.builder()
                .demoDto(demoDto)
                .build();
    }
}
