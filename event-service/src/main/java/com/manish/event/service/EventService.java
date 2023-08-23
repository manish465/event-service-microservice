package com.manish.event.service;

import com.manish.event.dto.CreateEventRequest;
import com.manish.event.entity.Event;
import com.manish.event.repository.EventRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class EventService {
    private final EventRepository eventRepository;

    public ResponseEntity<String> createEvent(String creatorId, @Valid CreateEventRequest createEventRequest){
        log.info("|| called createEvent from EventService class ||");

        // TODO: get the user id from user service and add this as creator id

        Event event = Event.builder()
                .name(createEventRequest.getName())
                .description(createEventRequest.getDescription())
                .capacity(createEventRequest.getCapacity())
                .creatorId(creatorId)
                .participantIdList(new ArrayList<>())
                .build();

        eventRepository.save(event);
        // TODO: send an event to the user service that will the event id to the event created list in user entity

        return new ResponseEntity<>("Event created", HttpStatus.CREATED);
    }
}
