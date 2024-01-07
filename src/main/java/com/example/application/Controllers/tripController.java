package com.example.application.Controllers;

import com.example.application.module.Trip;
import com.example.application.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class tripController {

    private TripService tripService;
    @Autowired
    private tripController(TripService tripService)
    {
        this.tripService=tripService;
    }

    @PostMapping("/create")
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }

    @GetMapping("/{tripId}")
    public Trip getTripById(@PathVariable String trip)
    {
       return tripService.getTripById(trip);
    }
    @GetMapping("/all")
    public List<Trip> getAllCabs() {
        return tripService.getAllTrips();
    }

    @PutMapping("/update")
    public void updateCab(@RequestBody Trip trip) {
        tripService.updateTrip(trip);
    }

    @DeleteMapping("/delete/{cabId}")
    public void deleteCab(@PathVariable String tripId) {
        tripService.deleteTrip(tripId);
    }
}
