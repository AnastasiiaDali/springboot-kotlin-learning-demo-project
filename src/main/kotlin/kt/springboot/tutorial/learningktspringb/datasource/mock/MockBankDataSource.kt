package kt.springboot.tutorial.learningktspringb.datasource.mock

import kt.springboot.tutorial.learningktspringb.datasource.BankDataSource
import kt.springboot.tutorial.learningktspringb.model.Bank
import org.springframework.stereotype.Repository
import java.util.NoSuchElementException

@Repository
class MockBankDataSource : BankDataSource {

    val banks = listOf(
            Bank("1234", 0.1, 1),
            Bank("2345", 17.1, 2),
            Bank("7689", 15.1, 5),
    )

    override fun retrieveBanks(): Collection<Bank> {
        return banks
    }

    override fun retrieveBank(accountNumber: String): Bank {
        return banks.firstOrNull() { it.accountNumber == accountNumber }
                ?: throw NoSuchElementException("Could not find a bank account with account number: $accountNumber")
    }
}