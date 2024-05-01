package com.talxan.ywitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@CrossOrigin
public class ywitter {

	public static void main(String[] args) {
		SpringApplication.run(ywitter.class, args);
	}

}
