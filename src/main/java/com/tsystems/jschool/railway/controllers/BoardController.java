package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.dto.BoardByStationDto;
import com.tsystems.jschool.railway.dto.SearchTripDto;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Station;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import com.tsystems.jschool.railway.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.util.List;

@Controller
public class BoardController {

    private static final Logger LOGGER = Logger.getLogger(BoardController.class);
    private final BoardService boardService;
    private final StationService stationService;

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
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "searchtrips";
    }

    @RequestMapping(value = "findTrip", method = RequestMethod.POST)
    public String findTrips(@ModelAttribute("searchTripDto") SearchTripDto searchTripDto, Model model){
        try {
            model.addAttribute("suitableTrips", boardService.findAllSuitableTrips(searchTripDto));
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
            return "searchtrips";
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
            return "searchtrips";
        }
        return "suitabletrips";
    }

    @RequestMapping(value = "boardByStation", method = RequestMethod.GET)
    public String getBoardByStation(Model model){
        try {
            model.addAttribute("allStations", stationService.getAllStations());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
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
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "showboard";
    }
}