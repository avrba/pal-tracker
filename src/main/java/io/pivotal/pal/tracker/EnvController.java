package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class EnvController {
    final String PORT;
    final String MEMORY_LIMIT;
    final String CF_INSTANCE_INDEX;
    final String CF_INSTANCE_ADDR;

    public EnvController(@Value("${PORT:NOT SET}") final String PORT,
                         @Value("${MEMORY_LIMIT:NOT SET}") final String MEMORY_LIMIT,
                         @Value("${CF_INSTANCE_INDEX:NOT SET}") final String CF_INSTANCE_INDEX,
                         @Value("${CF_INSTANCE_ADDR:NOT SET}") final String CF_INSTANCE_ADDR) {
        this.PORT = PORT;
        this.MEMORY_LIMIT = MEMORY_LIMIT;
        this.CF_INSTANCE_ADDR = CF_INSTANCE_ADDR;
        this.CF_INSTANCE_INDEX = CF_INSTANCE_INDEX;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        return Collections.unmodifiableMap(Stream.of(
                new AbstractMap.SimpleEntry<>("PORT", PORT),
                new AbstractMap.SimpleEntry<>("MEMORY_LIMIT", MEMORY_LIMIT),
                new AbstractMap.SimpleEntry<>("CF_INSTANCE_ADDR", CF_INSTANCE_ADDR),
                new AbstractMap.SimpleEntry<>("CF_INSTANCE_INDEX", CF_INSTANCE_INDEX)
        ).collect(Collectors.toMap((e)->e.getKey(),(e)->e.getValue())));
    }
}
