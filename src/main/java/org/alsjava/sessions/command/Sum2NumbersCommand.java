package org.alsjava.sessions.command;

import lombok.*;
import org.alsjava.sessions.model.network.request.Sum2NumbersRequest;
import org.alsjava.sessions.model.network.response.Sum2NumbersResponse;
import org.alsjava.sessions.pattern.command.Command;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Sum2NumbersCommand extends Command<Sum2NumbersResponse> {

    private Sum2NumbersRequest sum2NumbersRequest;
}
