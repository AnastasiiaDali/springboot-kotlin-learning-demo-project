package kt.springboot.tutorial.learningktspringb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class LearningKtSpringbApplication

fun main(args: Array<String>) {
	runApplication<LearningKtSpringbApplication>(*args)
}
