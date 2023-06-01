package com.kodilla.CarRentalBackend.scheduler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {
    private String recipient;
    private String subject;
    private String body;
}
