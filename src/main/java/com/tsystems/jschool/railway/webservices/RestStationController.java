package com.tsystems.jschool.railway.webservices;

import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Station;
import com.tsystems.jschool.railway.services.interfaces.StationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestStationController {

    private static final Logger LOGGER = Logger.getLogger(RestStationController.class);
    private final StationService stationService;

    @Autowired
    public RestStationController(StationService stationService) {
        this.stationService = stationService;
    }

    @RequestMapping(value = "/getAllStations", method = RequestMethod.GET)
    public List<String> getAllStationNames() {
        List<String> stationNames = null;
        try {
            List<Station> stations = stationService.getAllStations();
            stationNames = new ArrayList<>();
            for (Station station: stations){
                stationNames.add(station.getName());
            }
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
        }
        return stationNames;
    }
}
