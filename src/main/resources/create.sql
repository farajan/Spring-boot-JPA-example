CREATE TABLE users
(
 id_user bigserial PRIMARY KEY NOT NULL,
 fname varchar(255) NOT NULL,
 lname varchar(255) NOT NULL,
 age bigint
);

CREATE TABLE car
(
 id_car bigserial PRIMARY KEY NOT NULL,
 brand varchar(255) NOT NULL,
 horsepower bigint
);

CREATE TABLE users_car (
 id_user bigint NOT NULL,
 id_car bigint NOT NULL,
 FOREIGN KEY (id_user) REFERENCES users(id_user),
 FOREIGN KEY (id_car) REFERENCES car(id_car)
);