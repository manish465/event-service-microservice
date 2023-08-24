package com.manish.event.controller;

import com.manish.event.dto.CreateEventRequest;
import com.manish.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;

    @PostMapping
    @PreAuthorize("hasAuthority('NORMAL')")
    public ResponseEntity<String> createEvent(@RequestParam String creatorId, @RequestBody CreateEventRequest createEventRequest){
        log.info("|| called createEvent from EventController class ||");

        return eventService.createEvent(creatorId, createEventRequest);
    }
}
