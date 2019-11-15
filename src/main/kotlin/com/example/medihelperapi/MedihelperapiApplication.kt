package com.example.medihelperapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling



@SpringBootApplication
@EnableScheduling
class MedihelperapiApplication

fun main(args: Array<String>) {
	SpringApplication.run(MedihelperapiApplication::class.java)
}
