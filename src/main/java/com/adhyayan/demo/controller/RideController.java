package com.adhyayan.demo.controller;

import com.adhyayan.demo.dto.RideRequest;
import com.adhyayan.demo.dto.RideResponse;
import com.adhyayan.demo.model.Ride;
import com.adhyayan.demo.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/rides")
    public ResponseEntity<RideResponse> createRide(@Valid @RequestBody RideRequest request, Authentication authentication){
        String username = authentication.getName();
        RideResponse response = rideService.createRide(username,request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<RideResponse> completeRide(@PathVariable String rideId, Authentication authentication){
        String username = authentication.getName();
        RideResponse response = rideService.completeRide(username , rideId);
        return ResponseEntity.ok(response);
    }
}
