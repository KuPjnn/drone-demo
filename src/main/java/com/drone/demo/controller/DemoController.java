package com.drone.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DemoController {

    private final ServletWebServerApplicationContext server;

    @GetMapping
    public Map<String, String> getDemo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", "Drone Demo");
        info.put("port", String.valueOf(server.getWebServer().getPort()));
        return info;
    }

    @GetMapping("/demo")
    public List<String> getListString() {
        return Arrays.asList("Demo", "Long", "Oanh", "Maria Ozawa", "Miku");
    }

    @GetMapping("/container-info")
    public Map<String, String> getContainerInfo() {
        Map<String, String> containerInfo = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("/proc/self/cgroup"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("/docker/")) {
                    String[] parts = line.split("/");
                    String containerId = parts[parts.length - 1];
                    containerInfo.put("containerId", containerId);
                    break;
                }
            }
        } catch (IOException e) {
            containerInfo.put("error", "Could not read container information: " + e.getMessage());
        }
        return containerInfo;
    }

}
