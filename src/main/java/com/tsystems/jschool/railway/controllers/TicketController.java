package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.dto.SuitableTripDto;
import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.*;
import com.tsystems.jschool.railway.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class TicketController {

    private static final Logger LOGGER = Logger.getLogger(TicketController.class);
    private DateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.ENGLISH);
    private final BoardService boardService;
    private final WaypointService waypointService;
    private final TicketService ticketService;
    private final UserService userService;
    private final PassengerService passengerService;
    private String exception = "exception";

    @Autowired
    public TicketController(BoardService boardService, WaypointService waypointService, TicketService ticketService, UserService userService, PassengerService passengerService) {
        this.boardService = boardService;
        this.waypointService = waypointService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.passengerService = passengerService;
    }

    @RequestMapping("buyticket/{boardid}/{wpfromid}/{wptoid}")
    public String buyTicketPage(@PathVariable("boardid") int boardId, @PathVariable("wpfromid") int wpFromId,
                           @PathVariable("wptoid") int wpToId, Model model){
        LOGGER.info("try to get buy ticket page");
        try {
            Board board = boardService.findBoardById(boardId);
            Waypoint wpFrom = waypointService.findWaypointById(wpFromId);
            Waypoint wpTo = waypointService.findWaypointById(wpToId);
            SuitableTripDto trip = boardService.constractSuitableTripDto(board,wpFrom.getStation().getName(), wpTo.getStation().getName());
            Passenger passenger = passengerService.findPassengerByUser(findUserByEmail());
            if (passenger != null){
                model.addAttribute("passenger", passenger);
                String birthDate = dateFormat.format(passenger.getBirthdate());
                model.addAttribute("birthDate", birthDate);
            }
            model.addAttribute("trip", trip);
            model.addAttribute("board", board);
            model.addAttribute("wpFrom", wpFrom);
            model.addAttribute("wpTo", wpTo);
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "user/buyticket";
    }

    @RequestMapping(value = "addTicket", method = RequestMethod.POST)
    public String buyTicket(Model model, @RequestParam String firstName, @RequestParam String lastName,
                            @RequestParam String birthdate, @RequestParam String passport,
                            @RequestParam int boardId, @RequestParam int wpFromId, @RequestParam int wpToId,
                            @RequestParam String dateTime, RedirectAttributes redirectAttributes) throws ControllerException {
        LOGGER.info("passenger tries to buy a ticket");
        String redirectBuyTicket = "redirect:/buyticket/"+ boardId +"/" + wpFromId + "/" + wpToId;
        String message = "message";
        try {
            User user = findUserByEmail();
            Passenger passenger = passengerService.findPassengerByUser(user);
            if (firstName.trim().length() == 0) {
                throw new ControllerException(ErrorController.INCORRECT_PASSENGER_FIRSTNAME);
            }
            if (lastName.trim().length() == 0) {
                throw new ControllerException(ErrorController.INCORRECT_PASSENGER_LASTNAME);
            }
            if (passport.trim().length() < 10) {
                throw new ControllerException(ErrorController.INCORRECT_PASSENGER_PASSPORT);
            }

            try {
                Date birthdateDate = dateFormat.parse(birthdate);
                if (birthdateDate.after(new Date())){
                    throw new ControllerException(ErrorController.INCORRECT_PASSENGER_BIRTHDATE);
                }
                DateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date startDate = dateTimeFormat.parse(dateTime);
                if (passenger != null){
                    passenger.setFirstName(firstName);
                    passenger.setLastName(lastName);
                    passenger.setPassport(passport);
                    passenger.setBirthdate(birthdateDate);
                    passenger = passengerService.updatePassenger(passenger);
                } else{
                    passenger = new Passenger(firstName, lastName, birthdateDate, passport, user);
                    passenger = passengerService.addPassenger(passenger);
                }
                ticketService.buyTicket(passenger, boardId, wpFromId, wpToId, startDate);
            } catch (ParseException e) {
                throw new ControllerException(ErrorController.INCORRECT_DATE_FORMAT);
            }
            model.addAttribute(message, "You have bought a ticket successfully!");
        } catch (ControllerException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
            return redirectBuyTicket;
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
            return redirectBuyTicket;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(exception, e.getMessage());
            return redirectBuyTicket;
        }
        return "user/message_user";
    }

    @RequestMapping(value = "myTickets", method = RequestMethod.GET)
    public String getMyTickets (Model model){
        try {
            User user = findUserByEmail();
            model.addAttribute("tickets", ticketService.findAllTicketsByUser(user));
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "user/mytickets";
    }

    @RequestMapping(value = "deleteTicket/{id}", method = RequestMethod.GET)
    public String deleteBoard(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        try {
            Ticket ticket = ticketService.findTicketById(id);
            User user = findUserByEmail();
            List<SuitableTripDto> tickets = ticketService.findAllTicketsByUser(user);

            for (SuitableTripDto suitableTripDto: tickets){
                if (suitableTripDto.getTicketId() == id){
                    ticketService.deleteTicket(ticket);
                }
            }

            redirectAttributes.addFlashAttribute("message", "The ticket has been deleted successfully!");
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(exception, e.getMessage());
        }
        return "redirect:/myTickets";
    }

    public User findUserByEmail() throws ServiceException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findUserByEmail(userDetails.getUsername());
    }

}
