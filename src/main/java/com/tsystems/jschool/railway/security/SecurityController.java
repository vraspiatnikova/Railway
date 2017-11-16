package com.tsystems.jschool.railway.security;

import com.tsystems.jschool.railway.exceptions.ControllerException;
import com.tsystems.jschool.railway.exceptions.ErrorController;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class SecurityController {

    private static final Logger LOGGER = Logger.getLogger(SecurityController.class);
    private final UserService userService;

    @Autowired
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/signup" , method = RequestMethod.GET)
    public String getSignUpPage() {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUpUser(ModelMap model, @RequestParam String email, @RequestParam String password,
                             @RequestParam String confirm) {
        try {
            email = email.trim();
            Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher m = p.matcher(email);
            boolean isEmail = m.matches();
            if (!isEmail) {
                model.addAttribute("emailError", ErrorController.INCORRECT_EMAIL.getMessage());
                throw new ControllerException(ErrorController.INCORRECT_EMAIL);
            }
            if (password.length() < 4 || password.length() > 20) {
                model.addAttribute("passwordError", ErrorController.INCORRECT_PASSWORD.getMessage());
                throw new ControllerException(ErrorController.INCORRECT_PASSWORD);
            }
            if (!confirm.equals(password)) {
                model.addAttribute("confirmError", ErrorController.INCORRECT_CONFIRM_PASSWORD.getMessage());
                throw new ControllerException(ErrorController.INCORRECT_CONFIRM_PASSWORD);
            }
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            userService.regUser(email, hashedPassword);
            return "redirect:/login";
        } catch (ControllerException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
            return "/signup";
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
            model.addAttribute("exception", e.getError().getMessage());
            model.addAttribute("email", email);
            return "/signup";
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            model.addAttribute("exception", e.getMessage());
            return "/signup";
        }
    }

    @RequestMapping(value="/signout", method = RequestMethod.GET)
    public String signoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/boardByStation";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/admin_start_page", method = RequestMethod.GET)
    public String adminStartPage() {
        return "manager/admin_start_page";
    }

}
