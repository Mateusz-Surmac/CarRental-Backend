package com.kodilla.CarRentalBackend.observer;

import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.domain.Reservation;
import com.kodilla.CarRentalBackend.scheduler.EmailSender;
import com.kodilla.CarRentalBackend.scheduler.Mail;

import java.util.List;

public class ClientCarObserver implements CarObserver {

    private final Client client;
    private final EmailSender emailSender;

    public ClientCarObserver(Client client, EmailSender emailSender) {
        this.client = client;
        this.emailSender = emailSender;
    }

    @Override
    public void updateCarDamage(Car car) {
        List<Reservation> reservations = client.getReservations();

        String recipient = client.getEmail();
        String subject = "Car rental: Change Request";
        String body = "Dear " + client.getFirstName() + ",\n\n" +
                "We have noticed some damage on the car you reserved (ID: " + car.getId() + "). " +
                "Please contact us to discuss the situation and make necessary changes to your reservation.\n\n" +
                "Reservation Details:\n";

        for (Reservation reservation : reservations) {
            body += "Reservation ID: " + reservation.getId() + "\n" +
                    "Start Date: " + reservation.getRentalStart() + "\n" +
                    "End Date: " + reservation.getRentalEnd() + "\n" +
                    "Rental Place: " + reservation.getRentalPlace() + "\n" +
                    "Return Place: " + reservation.getReturnPlace() + "\n\n";
        }

        body += "Best regards,\n" +
                "Car Rental Team";

        Mail mail = new Mail(recipient, subject, body);
        emailSender.sendEmail(mail);
    }


}
