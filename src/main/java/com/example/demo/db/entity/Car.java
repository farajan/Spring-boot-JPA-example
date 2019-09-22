package com.example.demo.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_car;

    @Column(nullable = false)
    private String brand;

    private Integer horsepower;

    @ManyToOne(
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "users_car",
            joinColumns = { @JoinColumn(name = "id_car") },
            inverseJoinColumns = { @JoinColumn(name = "id_user")}
    )
    @JsonIgnore
    private User user;

    public Long getId_car() {
        return id_car;
    }

    public void setId_car(Long id_car) {
        this.id_car = id_car;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
