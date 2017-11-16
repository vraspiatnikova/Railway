package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.dto.BoardDto;
import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Board;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
                           @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                           @RequestParam("time") String time, @RequestParam int trainId,
                           @RequestParam("daysOfWeek") String daysOfWeek, RedirectAttributes redirectAttributes){
        LOGGER.info("try to add new trip: route number " + route + " , train name " + trainName
                + " start date "+ startDate+" end date "+endDate+" time "+time+" days of week "+daysOfWeek);
        try {
            List<DateTime> dates = new ArrayList<>();
            DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE dd MMMM yyyy");
            DateTimeFormatter pattern = fmt.withLocale(Locale.ENGLISH);
            DateTime start = pattern.parseDateTime(startDate);
            DateTime end = pattern.parseDateTime(endDate);
            if (end.isBefore(start)) throw new ControllerException(ErrorController.INCORRECT_START_END);
            String[] selectedDays = daysOfWeek.split(",");
            List<Integer> days = new ArrayList<>();
            for (String s: selectedDays){
                switch (s){
                    case "Sunday": days.add(DateTimeConstants.SUNDAY); break;
                    case "Monday": days.add(DateTimeConstants.MONDAY); break;
                    case "Tuesday": days.add(DateTimeConstants.TUESDAY); break;
                    case "Wednesday": days.add(DateTimeConstants.WEDNESDAY); break;
                    case "Thursday": days.add(DateTimeConstants.THURSDAY); break;
                    case "Friday": days.add(DateTimeConstants.FRIDAY); break;
                    case "Saturday": days.add(DateTimeConstants.SATURDAY); break;
                }
            }

            for (Integer i: days){
                DateTime startTmp = start;
                while (startTmp.isBefore(end)){
                    if ( startTmp.getDayOfWeek() == i ){
                        startTmp = startTmp.hourOfDay().setCopy(time.split(":")[0]);
                        startTmp = startTmp.minuteOfHour().setCopy(time.split(":")[1]);
                        dates.add(startTmp);
                    }
                    startTmp = startTmp.plusDays(1);
                }
            }
            if (!boardService.findBoardByTrainNameAndRoute(trainName, route).isEmpty())
                throw new ControllerException(ErrorController.DUPLICATE_TRAINS_ROUTE);

            boardService.addBoard(dates, trainName, route);

            LOGGER.info("new trip: route number " + route + " , train name " + trainName
                    + " start date "+ startDate+" end date "+endDate+" time "+time+" days of week "+daysOfWeek);
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
            List<BoardDto> boardDtos = boardService.getAllBoardsDto();
            model.addAttribute("board", new BoardDto());
            model.addAttribute("listBoards", boardDtos);
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "manager/alltrainsroutes";
    }

    @RequestMapping(value = "editTrip/{id}", method = RequestMethod.GET)
    public String editTrip(@PathVariable("id") int id, Model model){
        try {
            Board board = boardService.findBoardById(id);
            BoardDto boardDto = boardService.constructBoardDto(board);
            model.addAttribute("board", boardDto);
            model.addAttribute("id", id);
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "manager/editTrip";
    }

    @RequestMapping(value = "/updateBoard/{id}", method = RequestMethod.POST)
    public String updateBoard(@PathVariable("id") int id, @RequestParam String date, RedirectAttributes redirectAttributes){
        try {
            Board board = boardService.findBoardById(id);
            DateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy - HH:mm", Locale.ENGLISH);
            board.setDateTime(dateFormat.parse(date));
            boardService.updateBoard(board);
            redirectAttributes.addFlashAttribute("message", "The trip has been updated successfully!");
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        return "redirect:/allTrainsRoutes";
    }

    @RequestMapping(value = "deleteBoard/{id}", method = RequestMethod.GET)
    public String deleteBoard(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        try {
            Board board = boardService.findBoardById(id);
            boardService.deleteBoard(board);
            redirectAttributes.addFlashAttribute("message", "The trip has been deleted successfully!");
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        return "redirect:/allTrainsRoutes";
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
