package com.kodilla.CarRentalBackend.scheduler;

import com.kodilla.CarRentalBackend.strategy.EmailBodyStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private final EmailSender emailSender;
    private final EmailBodyStrategy reservationReminderMailStrategy;
    private final EmailBodyStrategy paymentReminderMailStrategy;
    private final EmailBodyStrategy reservationReminderWeatherMailStrategy;

    @Scheduled(cron = "0 0 8 1 * ?")
    public void sendReservationReminderEmail() {
        List<Mail> mails = reservationReminderMailStrategy.generateMails();
        mails.forEach(emailSender::sendEmail);
    }

    @Scheduled(cron = "0 0 8 * * MON")
    public void sendPaymentReminderEmail() {
        List<Mail> mails = paymentReminderMailStrategy.generateMails();
        mails.forEach(emailSender::sendEmail);
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendReservationReminderWeatherEmail() {
        List<Mail> mails = reservationReminderWeatherMailStrategy.generateMails();
        mails.forEach(emailSender::sendEmail);
    }
}