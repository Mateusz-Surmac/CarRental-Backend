package com.kodilla.CarRentalBackend.strategy;

import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.repository.ClientRepository;
import com.kodilla.CarRentalBackend.scheduler.Mail;
import com.kodilla.CarRentalBackend.service.RentalOrderDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PaymentReminderMailStrategy implements EmailBodyStrategy {

    private final ClientRepository clientRepository;
    private final RentalOrderDbService rentalOrderDbService;

    @Override
    public List<Mail> generateMails() {
        List<Mail> mails = new ArrayList<>();

        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            double amountDue = rentalOrderDbService.calculateAmountDueForClient(client.getId());
            if (amountDue > 0) {
                String recipient = client.getEmail();
                String subject = "Car rental: Payment Reminder";
                String body = buildPaymentReminderBody(client,amountDue);
                Mail mail = new Mail(recipient, subject, body);
                mails.add(mail);
            }
        }
        return mails;
    }

    private String buildPaymentReminderBody(Client client, double amountDue) {
        return "Dear " + client.getFirstName() + ",\n\n" +
                "This is a reminder about your outstanding payment for car rental services.\n\n" +
                "Payment Details:\n" +
                "Client ID: " + client.getId() + "\n" +
                "Amount Due: " + amountDue + "\n\n" +
                "Please make the payment as soon as possible to avoid any inconvenience.\n" +
                "If you have already made the payment, please disregard this reminder.\n\n" +
                "Thank you for using our car rental service.\n" +
                "Best regards,\n" +
                "Car Rental Team";
    }
}
