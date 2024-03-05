package com.drone.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public List<String> getListString() {
        return Arrays.asList("Demo", "Long", "Oanh", "Maria Ozawa", "Miku");
    }

}
