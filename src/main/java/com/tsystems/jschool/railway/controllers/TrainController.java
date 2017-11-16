package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Train;
import com.tsystems.jschool.railway.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TrainController {

    private static final Logger LOGGER = Logger.getLogger(TrainController.class);
    private final TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @RequestMapping(value = "trains", method = RequestMethod.GET)
    public String getAllTrains(Model model){
        LOGGER.info("try to add get all trains page");
        try {
            model.addAttribute("train", new Train());
            model.addAttribute("listTrains", this.trainService.getAllTrains());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "manager/trains";
    }

    @RequestMapping(value = "addTrain", method = RequestMethod.POST)
    public String addTrain(@ModelAttribute("train") Train train, RedirectAttributes redirectAttributes){
        LOGGER.info("try to add new train with name " + train.getName() + " and capacity " + train.getCapacity());
        try {
            if (train.getName().trim().length() != 0) {
                if (train.getCapacity() < 18 || train.getCapacity() > 1080)
                    throw new ControllerException(ErrorController.INCORRECT_CAPACITY);
                trainService.addTrain(train);
                LOGGER.info("new train with name " + train.getName() + " and capacity " + train.getCapacity() +" has been added");
                redirectAttributes.addFlashAttribute("message", "The train has been added successfully!");
            } else {
                throw new ControllerException(ErrorController.INCORRECT_TRAIN_NAME);
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
        return "redirect:/trains";
    }

    @RequestMapping(value = "editTrain/{id}", method = RequestMethod.GET)
    public String editTrain(@PathVariable("id") int id, Model model){
        try {
            Train train = trainService.findTrainById(id);
            model.addAttribute("train", train);
            model.addAttribute("id", id);
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
        }
        return "manager/editTrain";
    }

    @RequestMapping(value = "/updateTrain/{id}", method = RequestMethod.POST)
    public String updateBoard(@PathVariable("id") int id, @RequestParam String trainName, @RequestParam String capacity, RedirectAttributes redirectAttributes){
        try {
            Train train = trainService.findTrainById(id);
            train.setName(trainName);
            train.setCapacity(Integer.parseInt(capacity));
            trainService.updateTrain(train);
            redirectAttributes.addFlashAttribute("message", "The train has been updated successfully!");
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        return "redirect:/trains";
    }

    @RequestMapping(value = "deleteTrain/{id}", method = RequestMethod.GET)
    public String deleteBoard(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        try {
            Train train = trainService.findTrainById(id);
            trainService.deleteTrain(train);
            redirectAttributes.addFlashAttribute("message", "The train has been deleted successfully!");
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute("exception", e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        return "redirect:/trains";
    }
}
