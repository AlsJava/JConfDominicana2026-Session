package org.alsjava.sessions.command.handler;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.alsjava.sessions.command.Sum2NumbersCommand;
import org.alsjava.sessions.model.network.request.Sum2NumbersRequest;
import org.alsjava.sessions.model.network.response.Sum2NumbersResponse;
import org.alsjava.sessions.pattern.command.CommandEvent;
import org.alsjava.sessions.pattern.command.CommandHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Slf4j
@Component
@CommandEvent(command = Sum2NumbersCommand.class)
@Observed(name = "command.Sum2NumbersCommandHandler")
public class Sum2NumbersCommandHandler implements CommandHandler<Sum2NumbersResponse, Sum2NumbersCommand> {

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public Sum2NumbersResponse handle(Sum2NumbersCommand command) {
        Sum2NumbersRequest sum2NumbersRequest = command.getSum2NumbersRequest();
        BigDecimal sum = sum2NumbersRequest.getNumber1().add(sum2NumbersRequest.getNumber2());
        return Sum2NumbersResponse.builder()
                .result(sum)
                .build();
    }
}
