package com.example.demo.controller;

import com.example.demo.db.entity.Car;
import com.example.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class provides REST methods with regards to cars for a client.
 */
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    /**
     * This method provides get List of all cars from the database.
     *
     * @return A List of cars.
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Car> getAll() {
        return carService.getAll();
    }

    /**
     * This method provides get specific car.
     *
     * @param id An id of a car.
     * @return A car.
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public Car getById(@PathVariable Long id) {
        return carService.getById(id);
    }

    /**
     * This method provides get specific List of cars from the database.
     *
     * @param ids A List of ids.
     * @return A List of cars.
     */
    @RequestMapping(value = "/getCarsByIds", method = RequestMethod.GET)
    public List<Car> getCarsByIds(@RequestBody List<Long> ids) {
        return carService.getCarsByIds(ids);
    }

    /**
     * This method provides save a car to the database.
     *
     * @param car A new car which will be saved.
     * @return A new created car.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Car create(@RequestBody Car car) {
        return carService.create(car);
    }

    /**
     * This function updates parameters of a car in the database.
     *
     * @param car A car with new parameters.
     * @return A new updated car.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Car update(@RequestBody Car car) {
        return carService.update(car);
    }

    /**
     * This function deletes car from the database.
     *
     * @param id An id of a car which will be deleted.
     * @return A number of a records which were successfully saved.
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }


}
