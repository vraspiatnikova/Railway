package com.tsystems.jschool.railway.mail;

import com.tsystems.jschool.railway.dto.SuitableTripDto;
import com.tsystems.jschool.railway.persistence.Passenger;

public interface MailService {
    void sendBuyTicketEmail(Passenger passenger, SuitableTripDto ticket);
}
