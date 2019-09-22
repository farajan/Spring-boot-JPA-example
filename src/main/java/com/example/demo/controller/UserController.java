package com.example.demo.controller;

import com.example.demo.db.entity.User;
import com.example.demo.dto.TransferCarDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * This function provides get List of all users from the database.
     *
     * @return A List of users.
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<User> getAll() {
        return userService.getAll();
    }

    /**
     * This function provides get specific user.
     *
     * @param id An id of a user.
     * @return A users.
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    /**
     * This function provides save a user to the database.
     *
     * @param user A new user who will be saved.
     * @return A user who were saved.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    /**
     * This function provide update parameters of a user in the database.
     *
     * @param user A user with new parameters.
     * @return A updated user.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    /**
     * This function provides delete user from the database.
     *
     * @param id An id of a user who will be deleted.
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * This function provide assign a car to a user ownership.
     *
     * @param id_user An id of a user who will own a new car.
     * @param id_car An id of a car which a user will own.
     * @return A user with his cars.
     */
    @RequestMapping(value = "/{id_user}/buyCar/{id_car}", method = RequestMethod.POST)
    public User buyCar(@PathVariable Long id_user, @PathVariable Long id_car) {
        return userService.buyCar(id_user, id_car);
    }

    /**
     * This function provide remove a car for user ownership.
     *
     * @param id_user An id of a user who will be removed the car from.
     * @param id_car id of a car which will be deleted.
     * @return A user with his cars.
     */
    @RequestMapping(value = "/{id_user}/sellCar/{id_car}", method = RequestMethod.POST)
    public User sellCar(@PathVariable Long id_user, @PathVariable Long id_car) {
        return userService.sellCar(id_user, id_car);
    }

    /**
     * This function provides transfer a car from a owner to someone else.
     *
     * @param transferCarDto A information about ids of owner, new owner and car.
     * @return Both users with their cars.
     */
    @RequestMapping(value = "/transferCar", method = RequestMethod.POST)
    public List<User> transferCar(@RequestBody TransferCarDto transferCarDto) {
        return userService.transferCar(transferCarDto);
    }

}
