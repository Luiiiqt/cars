package com.lui.cars.controller;

import com.lui.cars.dto.CarDTO;
import com.lui.cars.exception.ResourceNotFoundException;
import com.lui.cars.repository.CarRepository;
import com.lui.cars.model.Car;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CarController {

    private final CarRepository carRepository;

    // Constructor injection of CarRepository
    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Home page: list all cars
    @GetMapping("/")
    public String index(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);

        // Log car makes to console (like in your reference code)
        cars.forEach(car -> System.out.println(car.getMake()));

        return "index"; // index.html template
    }

    // Show form to create a new car
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("car", new CarDTO()); // Using CarDTO for creation
        return "create"; // create.html template
    }

    // Process form to save new car
    @PostMapping("/save")
    public String save(
            @ModelAttribute("car") @Valid CarDTO carDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("car", carDTO);
            return "create"; // return to create form if validation fails
        }

        // Convert CarDTO to Car model and save to repository
        Car newCar = new Car();
        newCar.setMake(carDTO.getMake());
        newCar.setModel(carDTO.getModel());
        newCar.setYear(carDTO.getYear());
        newCar.setColor(carDTO.getColor());
        newCar.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        newCar.setBodyType(carDTO.getBodyType());
        newCar.setEngineType(carDTO.getEngineType());
        newCar.setTransmission(carDTO.getTransmission());

        carRepository.save(newCar); // Save the car object to the database

        return "redirect:/"; // Redirect to the home page
    }

    // Show details of a specific car
    @GetMapping("/show")
    public String show(@RequestParam int id, Model model) {
        Car car = carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car", id));

        model.addAttribute("car", car); // Add car details to the model
        return "show"; // show.html template to display car details
    }

    // Show form to edit an existing car
    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            model.addAttribute("error", "Car not found");
            return "error"; // error.html template if car not found
        }
        model.addAttribute("car", car);
        return "edit"; // edit.html template
    }

    // Process form to update car
    @PostMapping("/update")
    public String update(@ModelAttribute Car car) {
        carRepository.save(car); // save() updates if ID exists
        return "redirect:/";
    }

    // Delete car by ID
    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        carRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/view")
    public String view(@RequestParam int id, Model model) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));

        model.addAttribute("car", car); // Add car to model for view page
        return "view"; // view.html template to show car details
    }
}