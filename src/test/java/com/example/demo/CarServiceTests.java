package com.example.demo;

import com.example.demo.db.entity.Car;
import com.example.demo.db.repository.CarRepository;
import com.example.demo.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTests {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Test
    public void getAll_shouldPass() {
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        List<Car> cars = Arrays.asList(car1, car2, car3);

        when(carRepository.findAll()).thenReturn(cars);

        carService.getAll();

        verify(carRepository, times(1)).findAll();

        assertThat("Size is not equal to 3", carService.getAll().size(), is(3));
    }

    @Test
    public void getById_shouldPass() {
        carService.getById(1L);
        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    public void getCarsByIds_shouldPass() {
        Car car1 = new Car();
        car1.setId_car(1L);
        Car car2 = new Car();
        car2.setId_car(2L);
        Car car3 = new Car();
        car3.setId_car(3L);
        List<Car> cars = Arrays.asList(car1, car2, car3);
        List<Long> ids = new ArrayList<>(Arrays.asList(1L, 2L, 3L));

        when(carRepository.findAll()).thenReturn(cars);

        carService.getCarsByIds(ids);

        verify(carRepository, times(1)).findAll();

        assertThat("Size is not equal to 3", carService.getCarsByIds(ids).size(), is(3));
    }

    @Test
    public void create_shouldPass() {
        Car car = new Car();
        car.setBrand("test");
        car.setHorsepower(0);
        carService.create(car);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    public void update_shouldPass() {
        Car car = new Car();
        car.setId_car(1L);
        car.setBrand("test");

        carService.update(car);

        verify(carRepository, times(1)).save(car);
    }

    @Test
    public void delete_shouldPass() {
        Car car = new Car();
        car.setId_car(1L);

        Optional<Car> opCar = Optional.ofNullable(car);
        when(carRepository.findById(1L)).thenReturn(opCar);

        carService.delete(1L);
        verify(carRepository, times(1)).deleteById(1L);
    }

}
