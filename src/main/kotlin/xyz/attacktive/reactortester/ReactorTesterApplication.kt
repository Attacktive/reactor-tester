package xyz.attacktive.reactortester

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactorTesterApplication

fun main(args: Array<String>) {
	runApplication<ReactorTesterApplication>(*args)
}
