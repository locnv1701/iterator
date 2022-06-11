package api.iterator.backend.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE, value = "/liveness")
    String liveness() {
        return "It's alive!";
    }
}
