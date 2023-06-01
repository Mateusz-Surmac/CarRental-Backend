package com.kodilla.CarRentalBackend.strategy;

import com.kodilla.CarRentalBackend.api.openWeather.client.OpenWeatherClient;
import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.domain.Reservation;
import com.kodilla.CarRentalBackend.repository.ClientRepository;
import com.kodilla.CarRentalBackend.repository.ReservationRepository;
import com.kodilla.CarRentalBackend.scheduler.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationReminderWeatherMailStrategy implements EmailBodyStrategy {

    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;
    private final OpenWeatherClient openWeatherClient;

    @Override
    public List<Mail> generateMails() {
        List<Mail> mails = new ArrayList<>();
        List<Client> clients = clientRepository.findAll();

        for (Client client : clients) {
            List<Reservation> upcomingReservations = getUpcomingReservations(client.getId());
            for (Reservation reservation : upcomingReservations) {
                String recipient = client.getEmail();
                String subject = "Car rental: Reservation Reminder";
                String body = null;
                try {
                    body = buildReservationReminderBody(client, reservation);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Mail mail = new Mail(recipient, subject, body);
                mails.add(mail);
            }
        }

        return mails;
    }

    private List<Reservation> getUpcomingReservations(Long clientId) {
        LocalDate currentDate = LocalDate.now();
        LocalDate reminderDate = currentDate.plusDays(3);
        return reservationRepository.findByClientIdAndRentalStartBetween(clientId, currentDate, reminderDate);
    }

    private String buildReservationReminderBody(Client client, Reservation reservation) throws IOException {
        String weatherForecast = openWeatherClient.getWeatherInfo(reservation.getRentalPlace(),reservation.getRentalStart());
        return "Dear " + client.getFirstName() + ",\n\n" +
                "This is a reminder about your upcoming car rental reservation.\n\n" +
                "Reservation Details:\n" +
                "Reservation ID: " + reservation.getId() + "\n" +
                "Rental Start: " + reservation.getRentalStart() + "\n" +
                "Rental End: " + reservation.getRentalEnd() + "\n" +
                "Rental Place: " + reservation.getRentalPlace() + "\n" +
                "Return Place: " + reservation.getReturnPlace() + "\n\n" +
                "Weather Forecast:\n" +
                weatherForecast + "\n\n" +
                "We look forward to serving you!\n" +
                "Best regards,\n" +
                "Car Rental Team";
    }
}
