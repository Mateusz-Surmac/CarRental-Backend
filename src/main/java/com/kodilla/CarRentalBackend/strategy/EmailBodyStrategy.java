package com.kodilla.CarRentalBackend.strategy;

import com.kodilla.CarRentalBackend.scheduler.Mail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmailBodyStrategy {
    List<Mail> generateMails();
}
