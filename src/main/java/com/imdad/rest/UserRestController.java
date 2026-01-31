package com.imdad.rest;

import com.imdad.binding.DashboardCards;
import com.imdad.binding.LoginForm;
import com.imdad.binding.UserAccountForm;
import com.imdad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
	UserService userService;

    Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @PostMapping("/login")
    public String login(LoginForm loginForm) {

        logger.debug("Login process started");
        String login = userService.login(loginForm);

        if(login.equals("success")) {
            logger.info("Login successful");
            return "redirect:/dashboard" + loginForm.getEmail();
        }
        logger.error("Login failed");
        return login;
    }

    @GetMapping(
            value = "/dashboard",
            consumes = {"application/json", "application/xml"}
    )
    public ResponseEntity<DashboardCards> buildDashboardCards(
            @RequestParam ("email") String email){

        logger.debug("Build dashboard cards process started");
        UserAccountForm  user = userService.getUserByEmail(email);
        DashboardCards dashboardData = userService.getDashboardData();
        dashboardData.setUser(user);
        logger.debug("Build dashboard cards process finished");
        return new  ResponseEntity<>(dashboardData,HttpStatus.OK);
    }
}
