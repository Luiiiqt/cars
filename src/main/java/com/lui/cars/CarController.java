package com.lui.cars;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("car", new Car());
        return "create"; // create.html template
    }

    // Process form to save new car
    @PostMapping("/save")
    public String save(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/";
    }

    // Show form to edit existing car
    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            model.addAttribute("error", "Car not found");
            return "error"; // error.html template
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
}