package com.example.medihelperapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping

@SpringBootApplication
class MedihelperapiApplication

fun main(args: Array<String>) {
	runApplication<MedihelperapiApplication>(*args)
}
