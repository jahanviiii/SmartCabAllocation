package com.example.application.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.application.services.CabLocationService;

import java.util.Map;

@RestController
@RequestMapping("/api/cabs") // Base URL for cab-related endpoints
public class CabLocationController {

    private final CabLocationService cabLocationService;

    @Autowired
    public CabLocationController(CabLocationService cabLocationService) {
        this.cabLocationService = cabLocationService;
    }
    // Endpoint to get the last known location of a specific cab
    @GetMapping("/{cabId}/last-known-location")
    public ResponseEntity<String> getLastKnownLocation(@PathVariable String cabId) {
        String lastKnownLocation = cabLocationService.getLastKnownLocation(cabId);
        return ResponseEntity.ok("Last known location of cab " + cabId + ": " + lastKnownLocation);
    }
    // Endpoint to get the dynamic list of cabs and their current locations
    @GetMapping("/dynamic-cab-list")
    public ResponseEntity<Map<String, String>> getDynamicCabList() {
        Map<String, String> dynamicCabList = cabLocationService.getDynamicCabList();
        return ResponseEntity.ok(dynamicCabList);
    }

    // Endpoint to receive location updates from a cab device/app
    @PostMapping("/{cabId}/location")
    public ResponseEntity<String> receiveLocationUpdate(
            @PathVariable String cabId,
            @RequestBody String newLocation) {

        cabLocationService.receiveLocationUpdate(cabId, newLocation);
        return ResponseEntity.ok("Location update received for cab: " + cabId);
    }
}

