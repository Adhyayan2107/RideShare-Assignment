package com.adhyayan.demo.controller;

import com.adhyayan.demo.dto.RideResponse;
import com.adhyayan.demo.service.RideService;
import com.adhyayan.demo.dto.RideResponse;
import com.adhyayan.demo.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {
    private final RideService rideService;

    public DriverController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping("/rides/requests")
    public ResponseEntity<List<RideResponse>> getPendingRequests(){
        List<RideResponse> rides = rideService.getPendingRideRequests();
        return ResponseEntity.ok(rides);
    }

    @PostMapping("rides/{rideId}/accept")
    public ResponseEntity<RideResponse> acceptRide(@PathVariable String rideId , Authentication authentication){
        String username = authentication.getName();
        RideResponse response = rideService.acceptRide(username , rideId);
        return ResponseEntity.ok(response);
    }
}
