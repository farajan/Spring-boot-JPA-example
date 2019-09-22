package com.example.demo;

import com.example.demo.db.entity.Car;
import com.example.demo.db.entity.User;
import com.example.demo.db.repository.CarRepository;
import com.example.demo.db.repository.UserRepository;
import com.example.demo.dto.TransferCarDto;
import com.example.demo.service.CarService;
import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarService carService;

    @Test
    public void getAll_shouldPass() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        List<User> users = Arrays.asList(user1, user2, user3);

        when(userRepository.findAll()).thenReturn(users);

        userService.getAll();

        verify(userRepository, times(1)).findAll();

        assertThat("Size is not equal to 3", userService.getAll().size(), is(3));
    }

    @Test
    public void getById_shouldPass() {
        userService.getById(1L);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void create_shouldPass() {
        User user = new User();
        user.setFname("test");
        user.setLname("test");

        userService.create(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void update_shouldPass() {
        User user = new User();
        user.setId_user(1L);
        user.setFname("test");
        user.setLname("test");

        userService.update(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void delete_shouldPass() {
        User user = new User();
        user.setId_user(1L);

        Optional<User> opUser = Optional.ofNullable(user);
        when(userRepository.findById(1L)).thenReturn(opUser);

        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void buyCar_shouldPass() {
        User user = new User();
        user.setId_user(1L);
        user.setFname("test");
        user.setLname("test");

        Car car = new Car();
        car.setId_car(1L);

        List<Car> cars = new ArrayList<>();
        cars.add(car);
        user.setCars(cars);

        Optional<User> opUser = Optional.ofNullable(user);
        when(userRepository.findById(1L)).thenReturn(opUser);

        Optional<Car> opCar = Optional.ofNullable(car);
        when(carRepository.findById(1L)).thenReturn(opCar);
        when(carService.getById(1L)).thenReturn(car);

        userService.buyCar(1L, 1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void sellCar_shouldPass() {
        User user = new User();
        user.setId_user(1L);
        user.setFname("test");
        user.setLname("test");

        Car car = new Car();
        car.setId_car(1L);

        List<Car> cars = new ArrayList<>();
        cars.add(car);
        user.setCars(cars);

        Optional<User> opUser = Optional.ofNullable(user);
        when(userRepository.findById(1L)).thenReturn(opUser);

        userService.sellCar(1L, 1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void transferCar_shouldPass() {
        TransferCarDto transferCarDto = new TransferCarDto();
        transferCarDto.setId_seller(1L);
        transferCarDto.setId_buyer(1L);
        transferCarDto.setId_car(1L);

        Car car = new Car();
        car.setId_car(1L);

        List<Car> cars = new ArrayList<>();
        cars.add(car);

        User seller = new User();
        seller.setId_user(1L);
        seller.setFname("seller");
        seller.setLname("seller");
        seller.setCars(cars);

        User buyer = new User();
        buyer.setId_user(1L);
        buyer.setFname("buyer");
        buyer.setLname("buyer");

        Optional<User> opUser = Optional.of(seller);
        when(userRepository.findById(1L)).thenReturn(opUser);

        Optional<Car> opCar = Optional.of(car);
        when(carRepository.findById(1L)).thenReturn(opCar);
        when(carService.getById(1L)).thenReturn(car);

        userService.transferCar(transferCarDto);

        verify(userRepository, times(1)).save(seller);
        verify(userRepository, times(1)).save(buyer);
    }
}
