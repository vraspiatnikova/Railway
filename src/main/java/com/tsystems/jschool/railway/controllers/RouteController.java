package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.dto.NewRouteDto;
import com.tsystems.jschool.railway.dto.RouteDto;
import com.tsystems.jschool.railway.dto.SaveRouteDto;
import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Route;
import com.tsystems.jschool.railway.persistence.Train;
import com.tsystems.jschool.railway.services.interfaces.RouteService;
import com.tsystems.jschool.railway.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class RouteController {

    private static final Logger LOGGER = Logger.getLogger(RouteController.class);
    private final RouteService routeService;
    private final TrainService trainService;
    private String exception = "exception";
    private String message = "message";

    @Autowired
    public RouteController(RouteService routeService, TrainService trainService) {
        this.routeService = routeService;
        this.trainService = trainService;
    }

    @RequestMapping("addroute/{id}")
    public String addRoute(@PathVariable("id") int id, Model model){
        LOGGER.info("try to get add route page");
        try {
            Train train = trainService.findTrainById(id);
            if (train == null) throw new ServiceException(ErrorService.TRAIN_NOT_EXIST);
            model.addAttribute("train", train);
            model.addAttribute("allRoutes", routeService.getAllRoutes());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "manager/addroute";
    }

    @RequestMapping(value = "allRoutes", method = RequestMethod.GET)
    public String getAllRoutes(Model model){
        LOGGER.info("try to get all routes page");
        try {
            model.addAttribute("route", new Route());
            model.addAttribute("allRoutes", routeService.getAllRoutesDto());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "manager/routes";
    }

    @RequestMapping(value = "createRoute", method = RequestMethod.GET)
    public String createRoute(Model model){
        LOGGER.info("try to get new route page");
        try {
            NewRouteDto newRouteDto = routeService.getNewRoute();
            model.addAttribute("allStations", newRouteDto.getStations());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "manager/newroute";
    }

    @RequestMapping(value = "saveroute", method = RequestMethod.POST)
    public String saveRoute(@RequestParam("json") String json, Model model) {
        LOGGER.info("try to save new route");
        try {
            SaveRouteDto saveRouteDto = new SaveRouteDto();
            List<String> waypointStations = new ArrayList<>();
            List<Integer> waypointTravellTime=new ArrayList<>();
            List<Integer> waypointTravelStopTime=new ArrayList<>();

            JSONObject obj = new JSONObject(json);
            String routeNumber = obj.getString("routeNumber");
            if (routeNumber.length() == 0) throw new ControllerException(ErrorController.INCORRECT_ROUTE_NUMBER);
            saveRouteDto.setRouteNumber(routeNumber);
            JSONArray waypoints = obj.getJSONArray("waypoints");

            for (int i = 0; i < waypoints.length(); i++) {
                String station = waypoints.getJSONObject(i).getString("station");
                if (station.trim().length() == 0) throw new ControllerException(ErrorController.INCORRECT_STATION_NAME);
                String travelTimeStr = waypoints.getJSONObject(i).getString("travelTime");
                if (travelTimeStr.trim().length() == 0) throw new ControllerException(ErrorController.INCORRECT_TRAVEL_TIME);
                String stopTimeStr = waypoints.getJSONObject(i).getString("stopTime");
                if (stopTimeStr.trim().length() == 0) throw new ControllerException(ErrorController.INCORRECT_STOP_TIME);
                Integer travelTime = Integer.parseInt(travelTimeStr);
                Integer travelStopTime = Integer.parseInt(stopTimeStr);

                waypointStations.add(station);
                waypointTravellTime.add(travelTime);
                waypointTravelStopTime.add(travelStopTime);
            }

            Set<String> set = new HashSet<>(waypointStations);
            if (waypointStations.size() != set.size()) throw new ControllerException(ErrorController.DUPLICATE_STATIONS_IN_ROUTE);
            saveRouteDto.setWaypointStations(waypointStations);

            for (int i = 2; i < waypointTravellTime.size(); i++) {
                waypointTravellTime.set(i, waypointTravellTime.get(i-1)+waypointTravellTime.get(i));
            }
            saveRouteDto.setWaypointTravellTime(waypointTravellTime);

            for (int i = 0; i < waypointTravelStopTime.size(); i++) {
                waypointTravelStopTime.set(i, waypointTravellTime.get(i) + waypointTravelStopTime.get(i));
            }
            saveRouteDto.setWaypointTravelStopTime(waypointTravelStopTime);

            routeService.saveRoute(saveRouteDto);
            model.addAttribute(message, "The route has been saved successfully!");
        } catch (ControllerException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "message";
    }

    @RequestMapping(value = "editRoute/{id}", method = RequestMethod.GET)
    public String editTrain(@PathVariable("id") int id, Model model){
        try {
            Route route = routeService.findRouteById(id);
            if (route == null) throw new ServiceException(ErrorService.TRIP_NOT_EXIST);
            RouteDto routeDto = routeService.constructRouteDto(route);
            model.addAttribute("route", routeDto);
            model.addAttribute("id", id);
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "manager/editRoute";
    }

    @RequestMapping(value = "/updateRoute/{id}", method = RequestMethod.POST)
    public String updateBoard(@PathVariable("id") int id, @RequestParam String routeNumber, RedirectAttributes redirectAttributes){
        try {
            Route route = routeService.findRouteById(id);
            if (route == null) throw new ServiceException(ErrorService.TRIP_NOT_EXIST);
            if (routeNumber.length() == 0) throw new ControllerException(ErrorController.INCORRECT_ROUTE_NUMBER);
            route.setNumber(routeNumber);
            routeService.updateRoute(route);
            redirectAttributes.addFlashAttribute(message, "The route has been updated successfully!");
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(exception, e.getMessage());
        }
        return "redirect:/allRoutes";
    }

    @RequestMapping(value = "deleteRoute/{id}", method = RequestMethod.GET)
    public String deleteBoard(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        try {
            Route route = routeService.findRouteById(id);
            if (route == null) throw new ServiceException(ErrorService.TRIP_NOT_EXIST);
            routeService.deleteRoute(route);
            redirectAttributes.addFlashAttribute(message, "The route has been deleted successfully!");
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(exception, e.getMessage());
        }
        return "redirect:/allRoutes";
    }
}
