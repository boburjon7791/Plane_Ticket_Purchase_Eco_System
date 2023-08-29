package com.example.demo.controllers;

import com.example.demo.services.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.agent")
public class AgentController {
    public final AgentService agentService;
}
