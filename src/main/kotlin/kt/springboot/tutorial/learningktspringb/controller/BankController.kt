package kt.springboot.tutorial.learningktspringb.controller

import kt.springboot.tutorial.learningktspringb.model.Bank
import kt.springboot.tutorial.learningktspringb.service.BankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")

class BankController (private val service: BankService) {

    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()
}