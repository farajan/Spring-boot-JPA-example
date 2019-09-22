package com.example.demo.service;

import com.example.demo.db.entity.Car;
import com.example.demo.db.entity.User;
import com.example.demo.db.repository.UserRepository;
import com.example.demo.dto.TransferCarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarService carService;

    /**
     * This function returns all users from the database.
     *
     * @return A List of users.
     */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * This function returns specific user from the database.
     *
     * @param id An id of a user. This parameter can't be null.
     * @return A user.
     */
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * This function saves a user. Throw exception if first name or last name is null.
     *
     * @param user A new user who will be saved. This parameter can't be null
     * @return A user who where saved.
     */
    public User create(User user) {
        if (user.getFname() == null || user.getLname() == null) {
            throw new IllegalArgumentException("First name and last name are mandatory");
        }
        return userRepository.save(user);
    }

    /**
     * This function updates a user. Throw exception if id of user is null.
     *
     * @param user A user with new parameters. This parameter can't be null.
     * @return A updated user.
     */
    public User update(User user) {
        if(user.getId_user() == null || user.getFname() == null || user.getLname() == null) {
            throw new IllegalArgumentException("User ID is mandatory on update.");
        }
        return userRepository.save(user);
    }

    /**
     * This function deletes a user with all his cars. Throw exception if a user own some cars.
     *
     * @param id An id of a user which will be deleted. This parameter can't be null.
     */
    @Transactional
    public void delete(Long id) {
        if(getById(id) == null) {
            throw new IllegalArgumentException("User with this ID doesn't exist.");
        }
        userRepository.deleteById(id);
    }

    /**
     * This function assigns a car to a user ownership.
     *
     * @param id_user An id of a user who will own a new car. This parameter can't be null
     * @param id_car An id of a car which a user will own. This parameter can't be null
     * @return A user with his cars.
     */
    @Transactional
    public User buyCar(Long id_user, Long id_car) {
        User user = getById(id_user);
        if(user == null) {
            throw new IllegalArgumentException("User with this id doesn't exist.");
        }

        Car car = carService.getById(id_car);
        if(car == null) {
            throw new IllegalArgumentException("Car with this id doesn't exist.");
        }

        if(car.getUser() != null) {
            throw new IllegalArgumentException("Car with id: " + id_car + " belongs user with id: " + car.getUser().getId_user() +".");
        }
        user.getCars().add(car);
        return create(user);
    }

    /**
     * This function remove a car for user ownership.
     *
     * @param id_user An id of a user who will be removed the car from. This parameter can't be null
     * @param id_car id of a car which will be deleted. This parameter can't be null
     * @return A user with his cars.
     */
    @Transactional
    public User sellCar(Long id_user, Long id_car) {
        User user = getById(id_user);
        if(user == null) {
            throw new IllegalArgumentException("User with this id doesn't exist.");
        }

        List<Car> cars = user.getCars();
        final long preSize = cars.size();
        user.setCars(
                cars
                        .stream()
                        .filter(car -> !car.getId_car().equals(id_car))
                        .collect(Collectors.toList())
        );

        if(user.getCars().size() == preSize) {
            throw new IllegalArgumentException("User with id: " + id_user + " doesn't own car with id: " + id_car);
        }

        Car car = carService.getById(id_car);
        car.setUser(null);
        carService.create(car);

        return create(user);
    }

    /**
     * This function transfer a car from a owner to someone else. Throw exception if id of owner or new owner or car is null.
     *
     * @param dto A information about ids of owner, new owner and car.
     * @return Both users with his cars.
     */
    @Transactional
    public List<User> transferCar(TransferCarDto dto) {
        final Long id_seller = dto.getId_seller();
        final Long id_buyer = dto.getId_buyer();
        final Long id_car = dto.getId_car();

        if(id_seller == null || id_buyer == null || id_car == null) {
            throw new IllegalArgumentException("IDs for seller, buyer and car are mandatory.");
        }

        sellCar(id_seller, id_car);
        buyCar(id_buyer, id_car);
        return Arrays.asList(
                getById(id_seller),
                getById(id_buyer)
        );
    }

}
