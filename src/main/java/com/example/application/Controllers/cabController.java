package com.example.application.Controllers;

import com.example.application.module.Cab;
import com.example.application.services.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cabs")
public class cabController {
    private CabService cabService;

    @Autowired
    private cabController(CabService cabService)
    {

        this.cabService=cabService;
    }

    @PostMapping("/create")
    public Cab createCab(@RequestBody Cab cab) {
        return cabService.createCab(cab);
    }

    @GetMapping("/{cabId}")
    public Cab getCabById(@PathVariable String cabId) {
        return cabService.getCabById(cabId);
    }

    @GetMapping("/all")
    public List<Cab> getAllCabs() {
        return cabService.getAllCabs();
    }

    @PutMapping("/update")
    public void updateCab(@RequestBody Cab cab) {
        cabService.updateCab(cab);
    }

    @DeleteMapping("/delete/{cabId}")
    public void deleteCab(@PathVariable String cabId) {
        cabService.deleteCab(cabId);
    }

    // Endpoint to update cab status when a trip starts
    @PutMapping("/{cabId}/start-trip")
    public ResponseEntity<String> startNewTrip(@PathVariable String cabId) {
        cabService.startNewTrip(cabId);
        return ResponseEntity.ok("Trip started for cab: " + cabId);
    }
    // Endpoint to update cab status when a trip ends
    @PutMapping("/{cabId}/complete-trip")
    public ResponseEntity<String> completeTrip(@PathVariable String cabId) {
        cabService.completeTrip(cabId);
        return ResponseEntity.ok("Trip completed for cab: " + cabId);
    }
    // Endpoint to handle ride acceptance by the driver
    @PutMapping("/{cabId}/accept-ride")
    public ResponseEntity<?> acceptRide(@PathVariable String cabId) {
      try {
          cabService.acceptRide(cabId);
          return ResponseEntity.ok("Ride accepted successfully");
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }
}
