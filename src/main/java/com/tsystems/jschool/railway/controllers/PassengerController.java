package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.persistence.User;
import com.tsystems.jschool.railway.services.interfaces.PassengerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
public class PassengerController {

    private static final Logger LOGGER = Logger.getLogger(TicketController.class);
    private final PassengerService passengerService;
    private final TicketController ticketController;
    private String exception = "exception";
    private DateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.ENGLISH);

    @Autowired
    public PassengerController(PassengerService passengerService, TicketController ticketController) {
        this.passengerService = passengerService;
        this.ticketController = ticketController;
    }

    @RequestMapping(value = "myInfo", method = RequestMethod.GET)
    public String getMyInfo(Model model){
        try {
            LOGGER.info("try to get my info page");
            User user = ticketController.findUserByEmail();
            Passenger passenger = passengerService.findPassengerByUser(user);
            if (passenger != null) {
                model.addAttribute("passenger", passenger);
                String birthDate = dateFormat.format(passenger.getBirthdate());
                model.addAttribute("birthDate", birthDate);
            }
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        }
        return "user/myinfo";
    }

    @RequestMapping(value = "/updatePassenger/{id}", method = RequestMethod.POST)
    public String updatePassenger(@PathVariable("id") int id, @RequestParam String firstName, @RequestParam String lastName,
                                  @RequestParam String birthdate, @RequestParam String passport, RedirectAttributes redirectAttributes){
        try {
            Passenger passenger = passengerService.findPassengerById(id);
            if (passenger == null) throw new ControllerException(ErrorController.PASSENGER_NOT_FOUND);
            Date birthdateDate = dateFormat.parse(birthdate);
            passenger.setFirstName(firstName);
            passenger.setLastName(lastName);
            passenger.setBirthdate(birthdateDate);
            passenger.setPassport(passport);
            passengerService.updatePassenger(passenger);
            redirectAttributes.addFlashAttribute("message", "Your info has been updated successfully!");
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(exception, e.getMessage());
        }
        return "redirect:/myInfo";
    }

    @RequestMapping(value = "/updatePassenger/", method = RequestMethod.POST)
    public String createPassenger(@RequestParam String firstName, @RequestParam String lastName,
                                  @RequestParam String birthdate, @RequestParam String passport, RedirectAttributes redirectAttributes){
        try {
            User user = ticketController.findUserByEmail();
            Date birthdateDate = parseBirthdate(birthdate);
            if (birthdateDate.after(new Date())){
                throw new ControllerException(ErrorController.INCORRECT_PASSENGER_BIRTHDATE);
            }
            Passenger passenger = new Passenger(firstName, lastName, birthdateDate, passport, user);
            passengerService.addPassenger(passenger);
        } catch (ControllerException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(exception, e.getMessage());
        }
        return "redirect:/myInfo";
    }

    private Date parseBirthdate(String birthdate) throws ControllerException {
        Date dateOfBirth;
        try {
            dateOfBirth = dateFormat.parse(birthdate);
        } catch (ParseException e) {
            throw new ControllerException(ErrorController.INCORRECT_DATE_FORMAT);
        }
        return dateOfBirth;
    }
}
