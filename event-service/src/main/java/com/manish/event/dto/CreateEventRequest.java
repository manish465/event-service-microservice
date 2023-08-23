package com.manish.event.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequest {
    @Size(min = 3, max = 40, message = "event name should be no less then 3 and no more then 40 characters")
    private String name;
    @Size(min = 3, max = 100, message = "event description should be no less then 3 and no more then 100 characters")
    private String description;
    @Min(value = 5, message = "a event must have minimum capacity of 5 people")
    private int capacity;
}
