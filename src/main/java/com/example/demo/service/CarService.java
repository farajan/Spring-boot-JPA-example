package com.example.demo.service;

import com.example.demo.db.entity.Car;
import com.example.demo.db.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contain all business logic for operations with cars.
 */
@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    /**
     * This function returns all cars from the database.
     *
     * @return A List of cars.
     */
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    /**
     * This function returns specific car from the database.
     *
     * @param id An id of a car. This parameter can't be null.
     * @return A Cars.
     */
    public Car getById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    /**
     * This function returns specific cars from the database.
     *
     * @param ids A List of an ids of a cars. This parameter can't be null.
     * @return List of a cars.
     */
    public List<Car> getCarsByIds(List<Long> ids) {
        List<Car> cars = getAll();
        return cars
                .stream()
                .filter(car -> ids.contains(car.getId_car()))
                .collect(Collectors.toList());
    }

    /**
     * This function saves or updates a car. Throw exception if a brand of a car is null.
     *
     * @param car A new car which will be saved. This parameter can't be null
     * @return A new created car.
     */
    public Car create(Car car) {
        if(car.getBrand() == null) {
            throw new IllegalArgumentException("Car must have a brand.");
        }
        return carRepository.save(car);
    }

    /**
     * This function updates a car. Throw exception if a id of car is null.
     *
     * @param car A car with new parameters. This parameter can't be null.
     * @return A updated car.
     */
    public Car update(Car car) {
        if(car.getId_car() == null || car.getBrand() == null) {
            throw new IllegalArgumentException("Car must have a ID.");
        }
        return carRepository.save(car);
    }

    /**
     * This function deletes a car. Throw exception if a car belongs someone.
     *
     * @param id An id of a car which will be deleted. This parameter can't be null.
     */
    public void delete(Long id) {
        final Car car = getById(id);
        if(car == null) {
            throw new IllegalArgumentException("Car with this id doesn't exist.");
        }

        if(car.getUser() != null) {
            throw new IllegalArgumentException("User with id: " + car.getUser().getId_user() + " own this car.");
        }

        carRepository.deleteById(id);
    }

}
