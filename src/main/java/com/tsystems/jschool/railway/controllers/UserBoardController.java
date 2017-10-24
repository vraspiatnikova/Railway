package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.dto.BoardByStationDto;
import com.tsystems.jschool.railway.dto.SearchTripDto;
import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Station;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import com.tsystems.jschool.railway.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserBoardController {

    private static final Logger LOGGER = Logger.getLogger(UserBoardController.class);
    private final BoardService boardService;
    private final StationService stationService;

    @Autowired
    public UserBoardController(BoardService boardService, StationService stationService) {
        this.boardService = boardService;
        this.stationService = stationService;
    }

    @RequestMapping(value = "searchTripUser", method = RequestMethod.GET)
    public String searchTripsUser(Model model){
        LOGGER.info("try to get user's search trips page");
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
        return "user/searchtripsuser";
    }

    @RequestMapping(value = "findTripUser", method = RequestMethod.POST)
    public String findTripsUser(@ModelAttribute("searchTripDto") SearchTripDto searchTripDto, Model model, RedirectAttributes redirectAttributes){
        try {
            if(searchTripDto.getStationFrom().length() == 0 || searchTripDto.getStationTo().length() == 0)
                throw new ControllerException(ErrorController.INCORRECT_STATION_NAME);
            model.addAttribute("suitableTrips", boardService.findAllSuitableTrips(searchTripDto));
        } catch (ControllerException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
            return "redirect:/searchTripUser";
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
            return "redirect:/searchTripUser";
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/searchTripUser";
        }
        return "user/suitabletripsuser";
    }

    @RequestMapping(value = "boardByStationUser", method = RequestMethod.GET)
    public String getBoardByStationUser(Model model){
        try {
            model.addAttribute("allStations", stationService.getAllStations());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "user/boardbystationuser";
    }

    @RequestMapping(value = "showBoardByStationUser", method = RequestMethod.POST)
    public String showBoardByStationUser(@RequestParam String stationName, Model model){
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
        return "user/showboarduser";
    }
}
