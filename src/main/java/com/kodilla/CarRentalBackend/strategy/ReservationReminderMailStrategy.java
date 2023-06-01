package com.kodilla.CarRentalBackend.strategy;

import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.domain.Reservation;
import com.kodilla.CarRentalBackend.repository.ClientRepository;
import com.kodilla.CarRentalBackend.scheduler.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationReminderMailStrategy implements EmailBodyStrategy {

    private final ClientRepository clientRepository;

    @Override
    public List<Mail> generateMails() {
        List<Mail> mails = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(30);

        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            List<Reservation> reservations = client.getReservations();
            for (Reservation reservation : reservations) {
                LocalDate reservationStart = reservation.getRentalStart();
                if (reservationStart.isAfter(today) && reservationStart.isBefore(futureDate)) {
                    String recipient = client.getEmail();
                    String subject = "Car rental: Reservation Reminder";
                    String body = buildReservationBody(reservation);
                    Mail mail = new Mail(recipient, subject, body);
                    mails.add(mail);
                }
            }
        }
        return mails;
    }

    private String buildReservationBody(Reservation reservation) {
        return "Dear " + reservation.getClient().getFirstName() + ",\n\n" +
                "This is a reminder about your upcoming car rental reservation.\n\n" +
                "Reservation Details:\n" +
                "Start Date: " + reservation.getRentalStart() + "\n" +
                "End Date: " + reservation.getRentalEnd() + "\n" +
                "Rental Place: " + reservation.getRentalPlace() + "\n" +
                "Return Place: " + reservation.getReturnPlace() + "\n" +
                "Price: " + reservation.getPrice() + "\n\n" +
                "If you have any questions or need to make changes to your reservation, please contact us.\n\n" +
                "Thank you for choosing our car rental service.\n" +
                "Best regards,\n" +
                "Car Rental Team";
    }
}

