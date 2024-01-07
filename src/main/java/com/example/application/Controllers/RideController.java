package com.example.application.Controllers;


import com.example.application.module.Cab;
import com.example.application.module.Trip;
import com.example.application.module.User;
import com.example.application.services.CabService;
import com.example.application.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ride")
public class RideController {

    private final CabService cabService;
    private final TripService tripService;
    private static final double INITIAL_RADIUS = 0.1; // 100 meters
    private static final int MAX_WAIT_THRESHOLD = 5; // 5 minute for driver acceptance

    @Autowired
    public RideController(CabService cabService, TripService tripService) {
        this.cabService = cabService;
        this.tripService = tripService;
    }

    @PostMapping("/request")
    public ResponseEntity<?> handleRideRequest(@RequestBody String userLocation) {
        String tripId = UUID.randomUUID().toString();
        Trip trip = new Trip();
        User user =new User();
        trip.setTripId(tripId);
        String[] latLong = userLocation.split(",");
        if (latLong.length != 2) {
            return ResponseEntity.badRequest().build();
        }
        double latitude;
        double longitude;
        try {
            latitude = Double.parseDouble(latLong[0]);
            longitude = Double.parseDouble(latLong[1]);
        } catch (NumberFormatException e) {
            // Handle parsing errors
            return ResponseEntity.badRequest().build();
        }

        double searchRadius = INITIAL_RADIUS;
        Cab acceptedCab = null;

        for (int i = 0; i < MAX_WAIT_THRESHOLD; i++) {
            List<Cab> availableCabs = cabService.findAvailableCabsWithinRadius(latitude, longitude, searchRadius);
            if (availableCabs.isEmpty()) {
                searchRadius *= 2; // Double the search radius
                continue;
            }
            acceptedCab = waitForDriverAcceptance(availableCabs);

            if (acceptedCab != null) {
                tripService.finalizeRideDetails(acceptedCab, tripId);
                return ResponseEntity.ok(acceptedCab);
            }

            searchRadius *= 2;

        }
        return ResponseEntity.status(404).body("No cabs available within a reasonable distance.");
    }

    private Cab waitForDriverAcceptance(List<Cab> availableCabs) {
        long startTime = System.currentTimeMillis();
        long timeout = 60000; // 60 seconds

        while (System.currentTimeMillis() - startTime < timeout) {
            // Check if any cab from the availableCabs has accepted the ride
            for (Cab cab : availableCabs) {
                Cab currentCab = cabService.getCabById(cab.getCabId());
                if (currentCab != null && "Accepted".equals(currentCab.getStatus())) {
                    // Ride has been accepted
                    return currentCab;
                }
            }

            try {
                Thread.sleep(5000); // Sleep for 5 seconds before checking again
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null; // Handle thread interruption (e.g., application shutdown)
            }
        }

        return null;
    }


}


