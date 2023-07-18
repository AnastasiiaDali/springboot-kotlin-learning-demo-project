package kt.springboot.tutorial.learningktspringb.datasource

import kt.springboot.tutorial.learningktspringb.model.Bank

interface BankDataSource {
    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank
}