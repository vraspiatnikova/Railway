package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.dto.BoardDto;
import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Board;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ManagerBoardController {

    private static final Logger LOGGER = Logger.getLogger(ManagerBoardController.class);
    private final BoardService boardService;

    @Autowired
    public ManagerBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(value = "/addTrainRoute", method = RequestMethod.POST)
    public String addBoard(@RequestParam("route") String route, @RequestParam("trainName") String trainName,
                           @RequestParam("date") String date, @RequestParam int trainId, RedirectAttributes redirectAttributes){
        LOGGER.info("try to add new trip: route number " + route + " , train name " + trainName + " , date " + date);
        try {
            if (date.trim().length() != 0)
                throw new ControllerException(ErrorController.INCORRECT_DATE_FORMAT);
            Date boardDate;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            try {
                boardDate = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                throw new ControllerException(ErrorController.INCORRECT_DATE_FORMAT, e);
            }
            boardService.addBoard(boardDate, trainName, route);
            LOGGER.info("new trip: route number " + route + " , train name " + trainName + " , date " + date +" has been added");
            redirectAttributes.addFlashAttribute("message", "The trip has been added successfully!");
        } catch (ControllerException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
            return "redirect:/addroute/" + trainId;
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
            return "redirect:/addroute/" + trainId;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/addroute/" + trainId;
        }
        return "redirect:/allTrainsRoutes";
    }

    @RequestMapping(value = "/allTrainsRoutes", method = RequestMethod.GET)
    public String getAllTrainsAndRoutes(Model model){
        LOGGER.info("try to add get all trips page");
        try {
            model.addAttribute("board", new BoardDto());
            model.addAttribute("listBoards", this.boardService.getAllBoardsDto());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "manager/alltrainsroutes";
    }

    @RequestMapping("registredpassengers/{id}")
    public String findRegistredPassengers(@PathVariable("id") int id, Model model){
        LOGGER.info("try to add get all registered passengers page");
        try {
            Board board = boardService.findBoardById(id);
            List<Passenger> passengers = boardService.findRegisteredPassengers(board);
            model.addAttribute("allPassengers", passengers);
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "manager/registredpassengers";
    }
}
