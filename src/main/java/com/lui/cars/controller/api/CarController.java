package com.lui.cars.controller.api;

import com.lui.cars.dto.CarDTO;
import com.lui.cars.model.Car;
import com.lui.cars.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<Car> getCars() {
        return carService.findAll();
    }

    @PostMapping("/cars")
    public Car createCar(@Valid @RequestBody CarDTO car) {
        return carService.save(car);
    }

    @PutMapping("/cars/{id}")
    public Car updateCar(@PathVariable int id, @Valid @RequestBody CarDTO car){
        Car updateCar = carService.findById(id);
        if (updateCar == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID "+ id + " not found.");
        }
        return carService.updateCar(updateCar, car);
    }


    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable int id) {
        if(carService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID "+ id + " not found.");
        }
        carService.deleteCar(id);
    }
}