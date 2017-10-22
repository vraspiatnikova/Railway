package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Station;
import com.tsystems.jschool.railway.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StationController {

    private static final Logger LOGGER = Logger.getLogger(StationController.class);
    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @RequestMapping(value = "/stations", method = RequestMethod.GET)
    public String getAllStations(Model model){
        LOGGER.info("try to get all stations page");
        try {
            model.addAttribute("station", new Station());
            model.addAttribute("listStations", this.stationService.getAllStations());
            return "manager/stations";
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
            return "manager/stations";
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
            return "manager/stations";
        }
    }

    @RequestMapping(value = "addStation", method = RequestMethod.POST)
    public String addStation(@ModelAttribute("station") Station station, RedirectAttributes redirectAttributes){
        LOGGER.info("try to add new station with name "  + station.getName());
        try {
            if (station.getName().trim().length() != 0) {
                stationService.addStation(station);
                LOGGER.info("new station with name "  + station.getName() +" has been added");
                redirectAttributes.addFlashAttribute("message", "The station has been added successfully!");
            }
            else {
                throw new ControllerException(ErrorController.INCORRECT_STATION_NAME);
            }
        } catch (ControllerException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        return "redirect:/stations";
    }
}
