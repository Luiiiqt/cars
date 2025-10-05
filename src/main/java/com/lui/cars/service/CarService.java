package com.lui.cars.service;

import com.lui.cars.dto.CarDTO;
import com.lui.cars.exception.ResourceNotFoundException;
import com.lui.cars.model.Car;
import com.lui.cars.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void save(CarDTO carDTO) {
        Car car = new Car();
        car.setMake(carDTO.getMake());
        car.setYear(carDTO.getYear());
        car.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        car.setColor(carDTO.getColor());

        carRepository.save(car);
    }
}
