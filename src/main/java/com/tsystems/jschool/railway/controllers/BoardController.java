package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.dto.BoardByStationDto;
import com.tsystems.jschool.railway.dto.SearchTripDto;
import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Station;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import com.tsystems.jschool.railway.services.interfaces.StationService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
public class BoardController {

    private static final Logger LOGGER = Logger.getLogger(BoardController.class);
    private final BoardService boardService;
    private final StationService stationService;
    private String exception = "exception";

    @Autowired
    public BoardController(BoardService boardService, StationService stationService) {
        this.boardService = boardService;
        this.stationService = stationService;
    }

    @RequestMapping(value = "searchTrip", method = RequestMethod.GET)
    public String searchTrips(Model model){
        LOGGER.info("try to get search trips page");
        try {
            model.addAttribute("searchTripDto", new SearchTripDto());
            model.addAttribute("station", new Station());
            model.addAttribute("allStations", this.stationService.getAllStations());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "searchtrips";
    }

    @RequestMapping(value = "findTrip", method = RequestMethod.POST)
    public String findTrips(@ModelAttribute("searchTripDto") SearchTripDto searchTripDto, Model model, RedirectAttributes redirectAttributes){
        String redirectSearchTrip = "redirect:/searchTrip";
        try {
            if(searchTripDto.getStationFrom().length() == 0 || searchTripDto.getStationTo().length() == 0)
                throw new ControllerException(ErrorController.INCORRECT_STATION_NAME);

            DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE dd MMMM yyyy - HH:mm");
            DateTimeFormatter pattern = fmt.withLocale(Locale.ENGLISH);
            DateTime start = pattern.parseDateTime(searchTripDto.getDateTimeFrom());
            if (start.isBeforeNow()) throw new ControllerException(ErrorController.INCORRECT_START_DATE);
            DateTime end = pattern.parseDateTime(searchTripDto.getDateTimeTo());
            if (end.isBefore(start)) throw new ControllerException(ErrorController.INCORRECT_START_END);

            model.addAttribute("suitableTrips", boardService.findAllSuitableTrips(searchTripDto));
        } catch (ControllerException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
            return redirectSearchTrip;
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
            return redirectSearchTrip;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(exception, e.getMessage());
            return "redirect:/searchTrip";
        }
        return "suitabletrips";
    }

    @RequestMapping(value = "boardByStation", method = RequestMethod.GET)
    public String getBoardByStation(Model model){
        try {
            model.addAttribute("allStations", stationService.getAllStations());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "boardbystation";
    }

    @RequestMapping(value = "showBoardByStation", method = RequestMethod.POST)
    public String showBoardByStation(@RequestParam String stationName, Model model){
        try {
            List<BoardByStationDto> boardByStationDtos = boardService.getAllBoardByStationDto(stationName);
            model.addAttribute("allBoards", boardByStationDtos);
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "showboard";
    }
}
