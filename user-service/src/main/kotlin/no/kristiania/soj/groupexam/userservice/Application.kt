package no.kristiania.soj.groupexam.userservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application


fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}