package com.ead.course.dtos;

import lombok.Data;


import java.util.UUID;

import jakarta.validation.constraints.NotNull;

@Data
public class SubscriptionDto {

    @NotNull
    private UUID userId;
}
