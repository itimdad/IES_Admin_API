package com.imdad.rest;

import com.imdad.binding.LoginForm;
import com.imdad.binding.PlanForm;
import com.imdad.service.PlanService;
import com.imdad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlanRestController {

    @Autowired
    PlanService planService;

    @PostMapping(
            value = "/plan",
            consumes = {"application/json", "application/xml"}
    )
    public ResponseEntity<String> createPlan(PlanForm planForm) {

        boolean status = planService.addPlan(planForm);
        if (status) {
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(
            value = "/plan",
            produces = {"application/json", "application/xml"}
    )
    public ResponseEntity<List<PlanForm>> getPlans() {

        List<PlanForm> plans =
                planService.fetchPlans();

        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    @GetMapping(
            value = "/plan/{planId}",
            produces = {"application/json", "application/xml"}
    )
    public ResponseEntity<PlanForm> getPlanById(@PathVariable Integer planId) {
        PlanForm planForm = planService.fetchPlanById(planId);

        return new ResponseEntity<>(planForm, HttpStatus.OK);
    }

    @PutMapping(
            value = "/plan/{planId}/{status}"
    )
    public ResponseEntity<List<PlanForm>>  updatePlanStatus(@PathVariable Integer planId,
                                                    @PathVariable String status) {

        planService.changePlanStatus(planId, status);

        List<PlanForm> plans = planService.fetchPlans();

        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

}
