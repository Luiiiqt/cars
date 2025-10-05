package com.lui.cars.repository;

import com.lui.cars.dto.CarDTO;
import com.lui.cars.model.Car;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer>{

}
