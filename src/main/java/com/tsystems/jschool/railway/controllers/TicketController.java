package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.dto.SuitableTripDto;
import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Board;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.persistence.User;
import com.tsystems.jschool.railway.persistence.Waypoint;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import com.tsystems.jschool.railway.services.interfaces.TicketService;
import com.tsystems.jschool.railway.services.interfaces.UserService;
import com.tsystems.jschool.railway.services.interfaces.WaypointService;
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

@Controller
public class TicketController {

    private static final Logger LOGGER = Logger.getLogger(TicketController.class);
    private final BoardService boardService;
    private final WaypointService waypointService;
    private final TicketService ticketService;
    private final UserService userService;

    @Autowired
    public TicketController(BoardService boardService, WaypointService waypointService, TicketService ticketService, UserService userService) {
        this.boardService = boardService;
        this.waypointService = waypointService;
        this.ticketService = ticketService;
        this.userService = userService;
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
            model.addAttribute("trip", trip);
            model.addAttribute("board", board);
            model.addAttribute("wpFrom", wpFrom);
            model.addAttribute("wpTo", wpTo);
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "user/buyticket";
    }

    @RequestMapping(value = "addTicket", method = RequestMethod.POST)
    public String buyTicket(Model model, @RequestParam String firstName, @RequestParam String lastName,
                            @RequestParam String birthdate, @RequestParam String passport,
                            @RequestParam int boardId, @RequestParam int wpFromId, @RequestParam int wpToId,
                            @RequestParam String dateTime, RedirectAttributes redirectAttributes) throws ControllerException {
        LOGGER.info("passenger tries to buy a ticket");
        try {
            if (firstName.trim().length() == 0) {
                throw new ControllerException(ErrorController.INCORRECT_PASSENGER_FIRSTNAME);
            }
            if (lastName.trim().length() == 0) {
                throw new ControllerException(ErrorController.INCORRECT_PASSENGER_LASTNAME);
            }
            if (passport.trim().length() < 6) {
                throw new ControllerException(ErrorController.INCORRECT_PASSENGER_PASSPORT);
            }
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.findUserByEmail(userDetails.getUsername());
            try {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date birthdateDate = dateFormat.parse(birthdate);
                DateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date startDate = dateTimeFormat.parse(dateTime);
                Passenger passenger = new Passenger(firstName, lastName, birthdateDate, passport, user);
                ticketService.buyTicket(passenger, boardId, wpFromId, wpToId, startDate);
            } catch (ParseException e) {
                throw new ControllerException(ErrorController.INCORRECT_DATE_FORMAT);
            }
            model.addAttribute("message", "You have bought a ticket successfully!");
        } catch (ControllerException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("firstName", firstName);
            redirectAttributes.addFlashAttribute("lastName", lastName);
            redirectAttributes.addFlashAttribute("birthdate", birthdate);
            redirectAttributes.addFlashAttribute("passport", passport);
            redirectAttributes.addFlashAttribute("dateTime", dateTime);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
            return "redirect:/buyticket/"+ boardId +"/" + wpFromId + "/" + wpToId;
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("firstName", firstName);
            redirectAttributes.addFlashAttribute("lastName", lastName);
            redirectAttributes.addFlashAttribute("birthdate", birthdate);
            redirectAttributes.addFlashAttribute("passport", passport);
            redirectAttributes.addFlashAttribute("dateTime", dateTime);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
            return "redirect:/buyticket/"+ boardId +"/" + wpFromId + "/" + wpToId;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/buyticket/"+ boardId +"/" + wpFromId + "/" + wpToId;
        }
        return "user/message_user";
    }

    @RequestMapping(value = "myTickets", method = RequestMethod.GET)
    public String getMyTickets (Model model){
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.findUserByEmail(userDetails.getUsername());
            model.addAttribute("tickets", ticketService.findAllTicketsByUser(user));
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "user/mytickets";
    }
}
