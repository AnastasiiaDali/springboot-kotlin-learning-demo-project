package kt.springboot.tutorial.learningktspringb.service

import kt.springboot.tutorial.learningktspringb.datasource.BankDataSource
import kt.springboot.tutorial.learningktspringb.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> {
        return dataSource.retrieveBanks()
    }
}