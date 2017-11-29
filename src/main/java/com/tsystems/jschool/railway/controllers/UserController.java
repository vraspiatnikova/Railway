package com.tsystems.jschool.railway.controllers;

import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.User;
import com.tsystems.jschool.railway.persistence.roles.UserRole;
import com.tsystems.jschool.railway.services.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    private final UserService userService;
    private String exception = "exception";
    private String message = "message";
    private String redirectUsers = "redirect:/users";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String getAllUsers(Model model){
        try {
            model.addAttribute("listUsers", userService.getAllUsers());
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "dba/users";
    }

    @RequestMapping(value = "newUser", method = RequestMethod.GET)
    public String getNewUser(Model model){
        model.addAttribute("roles", UserRole.values());
        return "dba/newuser";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addNewUser(@RequestParam String email, @RequestParam String password,
                             @RequestParam String confirm, @RequestParam String role, RedirectAttributes redirectAttributes){
        String redirectNewUser = "redirect:/newUser";
        try {
            email = email.trim();
            Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher m = p.matcher(email);
            boolean isEmail = m.matches();
            if (!isEmail) {
                throw new ControllerException(ErrorController.INCORRECT_EMAIL);
            }
            if (password.length() < 4 || password.length() > 20) {
                throw new ControllerException(ErrorController.INCORRECT_PASSWORD);
            }
            if (!confirm.equals(password)) {
                throw new ControllerException(ErrorController.INCORRECT_CONFIRM_PASSWORD);
            }
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(email, hashedPassword, UserRole.valueOf(role));
            userService.addUser(user);
            redirectAttributes.addFlashAttribute(message, "You have registered a new user successfully!");
        } catch (ControllerException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
            return redirectNewUser;
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
            redirectAttributes.addFlashAttribute("email", email);
            return redirectNewUser;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(exception, e.getMessage());
            return redirectNewUser;
        }
        return redirectUsers;
    }

    @RequestMapping(value = "editUser/{id}", method = RequestMethod.GET)
    public String editTrain(@PathVariable("id") int id, Model model){
        try {
            User user = userService.findUserById(id);
            model.addAttribute("user", user);
            model.addAttribute("roles", UserRole.values());
            model.addAttribute("id", id);
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute(exception, e.getMessage());
        }
        return "dba/editUser";
    }

    @RequestMapping(value = "/updateUser/{id}", method = RequestMethod.POST)
    public String updateBoard(@PathVariable("id") int id, @RequestParam String role, RedirectAttributes redirectAttributes){
        try {
            User user = userService.findUserById(id);
            user.setRole(UserRole.valueOf(role));
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute(message, "The user has been updated successfully!");
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(exception, e.getMessage());
        }
        return redirectUsers;
    }

    @RequestMapping(value = "deleteUser/{id}", method = RequestMethod.GET)
    public String deleteBoard(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        try {
            User user = userService.findUserById(id);
            userService.deleteUser(user);
            redirectAttributes.addFlashAttribute(message, "The user has been deleted successfully!");
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            redirectAttributes.addFlashAttribute(exception, e.getError().getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(exception, e.getMessage());
        }
        return redirectUsers;
    }
}
