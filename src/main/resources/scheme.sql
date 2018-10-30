﻿DROP DATABASE IF EXISTS car_sharing;
CREATE DATABASE IF NOT EXISTS car_sharing;
USE car_sharing;
CREATE TABLE IF NOT EXISTS auto
(
	id INT NOT NULL AUTO_INCREMENT,
	brand VARCHAR(32) NOT NULL,
	model VARCHAR(32) NOT NULL,
	vin VARCHAR(17) NOT NULL,
	made_year INT NOT NULL,
	PRIMARY KEY(id),
	UNIQUE KEY(vin)
);

CREATE TABLE IF NOT EXISTS customer
(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(64) NOT NULL,
	birth_year INT NOT NULL,
	passport_number VARCHAR(10) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE KEY(passport_number)
);

CREATE TABLE IF NOT EXISTS auto_customer
(
	id INT NOT NULL AUTO_INCREMENT,
	auto_id INT NOT NULL,
	customer_id INT NOT NULL,
	start_rent TIMESTAMP NOT NULL,
	end_rent TIMESTAMP NOT NULL,
	closed_rent TIMESTAMP NOT NULL,
	status ENUM('active', 'active expired', 'closed', 'closed expired') NOT NULL,
	CHECK(start_loan < end_loan),
	KEY         (auto_id),
    KEY         (customer_id), 
	FOREIGN KEY (auto_id) REFERENCES auto (id) ON DELETE CASCADE,
	FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
	PRIMARY KEY (id),
	KEY fk_auto (auto_id),
	KEY fk_customer (customer_id),
    CONSTRAINT fk_auto FOREIGN KEY (auto_id) REFERENCES auto (id),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
);

INSERT INTO auto (brand, model, vin, made_year) VALUES("Mazda", "6", "43fdjwew432", 2012);
INSERT INTO auto (brand, model, vin, made_year) VALUES("Ford", "Eco Sport", "34rte322eww", 2015);
INSERT INTO auto (brand, model, vin, made_year) VALUES("Hyundai", "Sonata", "ew2232wsds", 2018);