package org.alsjava.sessions.controller;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.alsjava.sessions.model.network.request.Sum2NumbersRequest;
import org.alsjava.sessions.model.network.response.Sum2NumbersResponse;
import org.alsjava.sessions.service.ExampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/example")
@Observed(name = "controller.ExampleController")
public class ExampleController {

    private final ExampleService exampleService;

    @PostMapping("/sum")
    public ResponseEntity<Sum2NumbersResponse> create(@RequestBody Sum2NumbersRequest sum2NumbersRequest) {
        return ResponseEntity.ok(exampleService.sum2Numbers(sum2NumbersRequest));
    }

}
