package com.imdad.rest;

import com.imdad.binding.DashboardCards;
import com.imdad.binding.LoginForm;
import com.imdad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
	UserService userService;

    @PostMapping("login")
    public String login(LoginForm loginForm) {

        String login = userService.login(loginForm);

        if(login.equals("success")) {
            return "redirect:/dashboard" + loginForm.getEmail();
        }
        return login;
    }

    public ResponseEntity<DashboardCards> buildDashboardCards(
            @RequestParam ("email") String email){


        DashboardCards dashboardData = userService.getDashboardData();
        Object ResponseEntity;
        return new  ResponseEntity<>(dashboardData,HttpStatus.OK);
    }
}
